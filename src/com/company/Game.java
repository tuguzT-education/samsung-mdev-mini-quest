package com.company;

import java.util.Random;
import java.util.Scanner;

public class Game implements GameChecker{
    private Scanner scan = new Scanner(System.in);
    private Inner enemy = new Inner();
    private Inner player = new Inner();

    Game(int playerHealth, int enemyHealth) {
        this.player.setHealth(playerHealth);
        this.enemy.setHealth(enemyHealth);
    }

    public static class Inner {
        private int health;
        public void setHealth(int health) {
            this.health = health;
        }
        public int getHealth() {
            return health;
        }
        public int getArmor() {
            return Math.abs(new Random().nextInt() % 10);
        }
        public int getPower() {
            return Math.abs(new Random().nextInt() % 30);
        }
        public void attack(Inner target) {
            int temp = Math.abs(target.getArmor() - this.getPower());
            target.setHealth(target.getHealth() - temp);
        }
    }

    public void printInfo() {
        System.out.println("Здоровье игрока: " + player.getHealth() + "\nЗдоровье врага: " + enemy.getHealth());
    }

    public void start() {
        printInfo();
        System.out.println("Победите врага! Защищаться или атаковать?");
        input();
    }

    public boolean endGame() {
        if (player.getHealth() <= 0) {
            System.out.println("Вы проиграли!");
            return true;
        } else if (enemy.getHealth() <= 0) {
            System.out.println("Вы победили!");
            return true;
        }
        return false;
    }

    public void checkAnswer(String string) {
        switch (string) {
            case "Защищаться":
                enemy.attack(player);
                if (endGame()) {
                    return;
                } else if (new Random().nextBoolean()) {
                    printInfo();
                    System.out.println("Вы контратаковали!");
                    player.attack(enemy);
                }
                break;
            case "Атаковать":
                player.attack(enemy);
                if (endGame()) {
                    return;
                } else if (new Random().nextBoolean()) {
                    printInfo();
                    System.out.println("Противник контратаковал!");
                    enemy.attack(player);
                }
                break;
            default:
                System.out.println("Ответ не соответствует предложенным!\nПопробуйте снова:");
                input();
                break;
        }
    }

    public void input() {
        String string = scan.nextLine();
        checkAnswer(string);
        if (!endGame()) {
            start();
        }
    }
}
