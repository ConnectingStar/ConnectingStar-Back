package connectingstar.tars.common.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import connectingstar.tars.common.utils.MDCUtils;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 에러 로그를 Discord로 전송하기 위한 Appender
 *
 * @author 송병선
 */
@Slf4j
@Setter
public class DiscordLogbackAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

  private String discordWebhookUrl;

  /**
   * 에러 로그 전송
   *
   * @param event 로깅 이벤트
   */
  @Override
  protected void append(ILoggingEvent event) {
    if (event.getLevel().isGreaterOrEqual(Level.ERROR)) {
      toDiscord(event);
    }
  }

  private void toDiscord(ILoggingEvent event) {
    DiscordWebHook discordWebhook = new DiscordWebHook(discordWebhookUrl);
    Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
    Color messageColor = Color.red;

    String header = escapeJsonInternal(mdcPropertyMap.get(MDCUtils.HEADER_MAP_MDC));
    String body = escapeJsonInternal(mdcPropertyMap.get(MDCUtils.BODY_MDC).replaceAll("\\\\n|(^\"|\"$)", "\n"));
    String param = escapeJsonInternal(mdcPropertyMap.get(MDCUtils.PARAMETER_MAP_MDC));

    discordWebhook.addEmbed(new EmbedObject()
        .setTitle("[에러 내용]")
        .setColor(messageColor)
        .setDescription(event.getFormattedMessage())
        .addField(MDCUtils.REQUEST_URI_MDC, mdcPropertyMap.get(MDCUtils.REQUEST_URI_MDC), true)
        .addField("[발생 시간]", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), true)
        .addField(MDCUtils.USER_IP_MDC, mdcPropertyMap.get(MDCUtils.USER_IP_MDC), false)
        .addField(MDCUtils.HEADER_MAP_MDC, header, false)
        .addField(MDCUtils.PARAMETER_MAP_MDC, param, false)
        .addField(MDCUtils.BODY_MDC, body, false));

    IThrowableProxy throwable = event.getThrowableProxy();
    if (Objects.nonNull(throwable)) {
      String exception = ThrowableProxyUtil.asString(throwable).substring(0, 2000);
      discordWebhook.addEmbed(
          new EmbedObject()
              .setTitle("[Exception 상세 내용]")
              .setColor(messageColor)
              .setDescription(escapeJsonInternal(exception))
      );
    }

    try {
      discordWebhook.execute();
    } catch (Exception e) {
      log.info("에러: " + e.getMessage());
    }
  }

  private String escapeJsonInternal(final String input) {
    final StringBuilder builder = new StringBuilder(input.length() * 2);
    for (int i = 0; i < input.length(); i++) {
      final char ch = input.charAt(i);
      switch (ch) {
        case '"':
          builder.append("\\\"");
          break;
        case '\\':
          builder.append("\\\\");
          break;
        case '\b':
          builder.append("\\b");
          break;
        case '\f':
          builder.append("\\f");
          break;
        case '\n':
          builder.append("\\n");
          break;
        case '\r':
          builder.append("\\r");
          break;
        case '\t':
          builder.append("\\t");
          break;
        default:
          if (ch < ' ' || (ch >= '\u0080' && ch < '\u00a0')
              || (ch >= '\u2000' && ch < '\u2100')) {
            builder.append("\\u").append(Integer.toHexString(ch | 0x10000).substring(1));
          } else {
            builder.append(ch);
          }
      }
    }
    return builder.toString();
  }
}
