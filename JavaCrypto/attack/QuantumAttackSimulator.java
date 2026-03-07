package JavaCrypto.attack;

public class QuantumAttackSimulator {

    public static String simulateAttack(String algorithm) {
        if (algorithm.equalsIgnoreCase("RSA")) {
            return "BROKEN_UNDER_QUANTUM_ATTACK";
        } else {
            return "QUANTUM_SAFE";
        }
    }
}
