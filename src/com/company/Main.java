package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 900;
    public static int bossDamage = 50;
    public static String bossDefence = "";
    public static int medicHealth = 500;
    public static int golemHealth = 800;
    public static int luckyHealth = 300;
    public static boolean isDodged = false;
    public static boolean isStunned = false;


    public static int[] heroesHealth = {260, 250, 270, medicHealth, golemHealth, luckyHealth, 320, 280};
    public static int[] heroesDamage = {15, 20, 10, 0, 10, 35, 25, 25};
    public static String[] heroesAttackType = {
            "Physical", "Magical", "Kinetic", "Medical", "Golem", "Lucky", "Berserk", "Thor"};
    public static Random random = new Random();


    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        thor();
        if (bossHealth > 0 && !isStunned) {
            chooseBossDefence();
            bossHits();
            lucky();
            golem();
            medic();
            berserk();
        } else System.out.println("\t\tBOSS STUNNED");
        heroesHit();
        printStatistics();
    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        if (randomIndex == 3) {
            chooseBossDefence();
        } else {
            bossDefence = heroesAttackType[randomIndex];
            System.out.println("Boss choose: " + bossDefence);
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 &&
                heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefence == heroesAttackType[i]) {
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else if (!isStunned){
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                        System.out.println(
                                "Critical Damage: " + ((heroesDamage[i] * coeff) - heroesDamage[i]));
                    }

                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("++++++++++++++");
        System.out.println("Boss health: " + bossHealth + " ["
                + bossDamage + "]\n");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i] + " ["
                    + heroesDamage[i] + "]");

        }
        System.out.println("++++++++++++++");
    }

    public static void medic() {
        int randomHero = random.nextInt(heroesAttackType.length);
        int healPoint = random.nextInt(40) + 10;

        if (medicHealth > 0) {
            if (randomHero == 3) { //не медик
                medic();
            } else if (heroesHealth[randomHero] < 100 && heroesHealth[randomHero] > 0) {
                heroesHealth[randomHero] += healPoint;
                System.out.println(heroesAttackType[randomHero] + " healed for: " + healPoint);
            } else if (heroesHealth[randomHero] == 0) {
                System.out.println("Medic cant heal: " + heroesAttackType[randomHero]);
            }
        }
    }

    public static void golem() {
        int golemTakenDamage = bossDamage / 5;
        int aliveHeroes = 0;

        for (int i = 0; i < heroesDamage.length; i++) {
            if (i == 4) {
                continue;
            } else if (heroesHealth[i] > 0) {
                aliveHeroes++;
                heroesHealth[i] += golemTakenDamage;
            }
        }
        int sumDamage = golemTakenDamage * aliveHeroes;
        if (heroesHealth[4] - sumDamage < 0) {
            heroesHealth[4] = 0;
        } else {
            heroesHealth[4] -= sumDamage;
            System.out.println("Golem take: " + sumDamage);
        }

    }

    public static void lucky() {
        isDodged = random.nextBoolean();
        if (isDodged && heroesHealth[5] - bossDamage > 0) {
            heroesHealth[5] += bossDamage - (bossDamage / 5);
            System.out.println("\t\tDODGE");
        }
    }

    public static void berserk() {
        int blockedDamage = random.nextInt(bossDamage - 10) + 10;

        if (blockedDamage % 5 == 0) {
            if (heroesHealth[6] > 0) {
                bossHealth -= blockedDamage;
                heroesHealth[6] += blockedDamage;
                System.out.println(heroesAttackType[6] + " blocked: " + blockedDamage);
            }
        } else berserk();
    }

    public static void thor() {
        isStunned = random.nextBoolean();
    }


}

