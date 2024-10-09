package connectingstar.tars.constellation.enums;

/**
 * 별자리를 식별하는 코드
 *
 * @author 이우진
 */
public enum ConstellationCode {
    /**
     * Dr타코, Octopus
     */
    OCTOPUS("OCTOPUS"),

    /**
     * 고든람쥐, Squirrel
     */
    SQUIRREL("SQUIRREL"),

    /**
     * 고래앵, Whale
     */
    WHALE("WHALE"),

    /**
     * 귄귄이, Penguin
     */
    PENGUIN("PENGUIN"),

    /**
     * 기프트, Gift
     */
    GIFT("GIFT"),

    /**
     * 뀨르14세, Mandarin
     */
    MANDARIN("MANDARIN"),

    /**
     * 누으마, Horse
     */
    HORSE("HORSE"),

    /**
     * 닭둘기, Pigeon
     */
    PIGEON("PIGEON"),

    /**
     * 따라앵, Parrot
     */
    PARROT("PARROT"),

    /**
     * 메라텔, Badger
     */
    BADGER("BADGER"),

    /**
     * 스터비, Bee
     */
    BEE("BEE"),

    /**
     * 용용이, Dino
     */
    DINO("DINO"),

    /**
     * 잉어퀸, Goldfish
     */
    GOLDFISH("GOLDFISH"),

    /**
     * 제트부기, Turtle
     */
    TURTLE("TURTLE"),

    /**
     * 치타코기, Cheetah
     */
    CHEETAH("CHEETAH"),

    /**
     * 타스, Tars
     */
    TARS("TARS");

    private final String value;

    ConstellationCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}