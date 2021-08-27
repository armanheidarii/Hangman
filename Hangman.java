import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;

public class Hangman {
    private static char[] ignore = new char[0];
    private static String word;
    private static StringBuilder dashWord;
    private static int counter = 0;
    private static StringBuilder XV;

    public static void printGoodBye() throws InterruptedException {
        System.out.println(Colors.YELLOW_BOLD_BRIGHT + """
                           ,%%%%%%%%,
                          %%o%%/%%%%%%
                         %%%%\\%%%<%%%%%
                         %%>%%%/%%%%o%%
                         %%%%%o%%\\%%//%
                         %\\o%\\%%/%o/%%'
                          '%%\\ `%/%%%'
                            '%| |%|%'
                              | | |
                              | | |
                              | | (O
                              | | |\\
                              | | |\\
                              | |
                              | |
                             /   \\
                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^""" + Colors.YELLOW_BOLD_BRIGHT + """

                """);
        Thread.sleep(3000);
        System.out.println(Colors.PURPLE_BOLD_BRIGHT + "         AP SPRING 1400");
        Thread.sleep(3000);
        System.out.println(Colors.PURPLE + "        \"DESIGN BY Arman\"\n\n");
        Thread.sleep(7000);
    }

    public static void updateXV() {
        for (int i = 0; i < XV.length(); i++) {
            if (XV.charAt(i) == 'V') {
                XV.replace(i, i + 1, "X");
                return;
            }
        }
    }

    public static void printXV() {
        System.out.println(Colors.RED_BRIGHT);
        int i = 2;
        for (; i <= XV.lastIndexOf("X") + 2; i++) {
            System.out.print(XV.charAt(i));
        }
        System.out.print(Colors.GREEN_BRIGHT);
        for (; i < XV.length(); i++) {
            System.out.print(XV.charAt(i));
        }
        System.out.println(Colors.RESET);
    }

    public static void gallowsPlus() {
        if (word.length() > 9) {
            counter++;
        } else
            counter += 2;
    }

    public static void printGallows() {
        int numerator = 2 * (int) Math.floor(counter / 2.0);
        System.out.println(Colors.YELLOW_BRIGHT);
        if (numerator == 0) {
            System.out.println("----\n|\n|\n|\n|\n|");
        } else if (numerator == 2) {
            System.out.println("----\n|   |\n|\n|\n|\n|");
        } else if (numerator == 4) {
            System.out.println("----\n|   |\n|   O\n|\n|\n|");
        } else if (numerator == 6) {
            System.out.println("----\n|   |\n|   O\n|  /\n|\n|");
        } else if (numerator == 8) {
            System.out.println("----\n|   |\n|   O\n|  /|\n|\n|");
        } else if (numerator == 10) {
            System.out.println("----\n|   |\n|   O\n|  /|\\\n|\n|");
        } else if (numerator == 12) {
            System.out.println("----\n|   |\n|   O\n|  /|\\\n|  /\n|");
        } else if (numerator == 14) {
            System.out.println("----\n|   |\n|   O\n|  /|\\\n|  / \\\n|");
        }
        System.out.print(Colors.RESET);
    }

    public static String help(int index) throws InterruptedException {
        if (Account.scoreMinus(index)) {
            while (true) {
                int randIndex = (int) (Math.random() * dashWord.length());
                char help = dashWord.charAt(randIndex);
                if (help == '-') {
                    String ans = Character.toString(word.charAt(randIndex));
                    enqueue(ans.charAt(0));
                    return ans;
                }
            }
        }
        return "-1";
    }

    public static int leaderBoard(int index) throws InterruptedException {
        Scanner in = new Scanner(System.in);
        index = Account.sort(index);
        User[] users = Account.getUsers();
        Tool.clearScreen();
        String n = "1";
        while (!n.equals("0")) {
            System.out.println(Colors.WHITE_BOLD_BRIGHT + Colors.PURPLE_BACKGROUND + "Leader board: (Press 0 to back)\n" + Colors.RESET);
            for (int i = 0; i < Account.getUsersLength(); i++) {
                if (i == index) {
                    System.out.printf(Colors.PURPLE_BRIGHT + "* %s: %d\n" + Colors.RESET, users[i].getUsername(), users[i].getScore());
                    continue;
                }
                System.out.printf(Colors.PURPLE + "%s: %d\n" + Colors.RESET, users[i].getUsername(), users[i].getScore());
            }
            n = in.next();
            if (!n.equals("0")) {
                Tool.invalid();
            }
        }
        return index;
    }

    public static boolean trueFalse(char n) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == n) {
                return true;
            }
        }
        return false;
    }

    public static void printIgnore() {
        System.out.print(Colors.BLACK + Colors.WHITE_BACKGROUND + "ignore: ");
        if (ignore.length == 0) {
            System.out.println("empty");
            return;
        }
        for (char s : ignore) {
            System.out.print(s + " ");
        }
        System.out.println(Colors.RESET);
    }

    public static int enqueue(char n) {
        for (char c : ignore) {
            if (c == n)
                return -1;
        }
        char[] ignore1 = new char[ignore.length + 1];
        System.arraycopy(ignore, 0, ignore1, 0, ignore.length);
        ignore1[ignore1.length - 1] = n;
        ignore = ignore1;
        return 1;
    }

    public static void updateDashWord(String guess) {
        for (int i = 0; i < dashWord.length(); i++) {
            if (word.charAt(i) == guess.charAt(0)) {
                dashWord.replace(i, i + 1, Character.toString(guess.charAt(0)));
            }
        }
    }

    public static void dashBuilder() {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == ' ')
                dashWord.insert(i, ' ');
            else
                dashWord.insert(i, '-');
        }
    }

    public static void printMenu() {
        System.out.println(Colors.YELLOW_BOLD_BRIGHT +
                """
                         _    _
                        | |  | |
                        | |__| |  __ _ _ __   __ _ _ __ ___   __ _ _ __
                        | '__| |/ _` | '_ \\ / _` | '_ ` _ \\ / _` | '_ \\
                        | |  | | (_| | | | | (_| | | | | | | (_| | | | |
                        |_|  |_|\\__,_|_| |_|\\__, |_| |_| |_|\\__,_|_| |_|
                                            __/ |
                                           |___/
                                           |
                                           |
                                           (O
                                           |\\
                                           |\\


                        """);
        System.out.println(Colors.YELLOW_BOLD_BRIGHT + "1. signup / 2. login / 0.exit" + Colors.RESET);
    }

    public static void main(String[] args) throws InterruptedException {
        Tool.clearScreen();
        Scanner in = new Scanner(System.in);
        int kingKey;
        int index = kingKey = SignInUp.process();
        while (kingKey >= 0) {
            Tool.clearScreen();
            System.out.println(Colors.YELLOW_BOLD_BRIGHT + "1. start game / 2. show leaderboard / 0.logout" + Colors.RESET);
            String n = in.next();
            switch (n) {
                case "1" -> {
                    word = Tool.randomWord();
                    dashWord = new StringBuilder();
                    dashBuilder();
                    if (word.length() > 9) {
                        XV = new StringBuilder("| V | V | V | V | V | V | V | V | V | V | V | V | V | V |");
                    } else {
                        XV = new StringBuilder("| V | V | V | V | V | V | V |");
                    }
                    Tool.clearScreen();
                    System.out.println(Colors.WHITE_BOLD_BRIGHT + "Word is: " + Colors.YELLOW_BOLD_BRIGHT + word + Colors.RESET);
                    Thread.sleep(3000);
                    boolean flag = true;
                    String guess = null;
                    while (dashWord.indexOf("-") >= 0 && flag) {
                        boolean flag1 = true;
                        boolean flag2 = true;
                        while (flag && flag1) {
                            Tool.clearScreen();
                            System.out.println(Colors.WHITE_BOLD_BRIGHT + Colors.YELLOW_BACKGROUND + dashWord + Colors.RESET);
                            printGallows();
                            System.out.println();
                            printXV();
                            System.out.println();
                            printIgnore();
                            System.out.println();
                            System.out.println(Colors.YELLOW_BRIGHT + "Please enter your guess: (Press h- to help, 0 to exit)" + Colors.RESET);
                            guess = in.next();
                            if (guess.equals("h-")) {
                                guess = help(index);
                                if (guess.equals("-1"))
                                    flag2 = false;
                                flag1 = false;
                            } else if (guess.charAt(0) == '0') {
                                if (!Account.isHelp()) {
                                    Account.getUsers()[index].setScore(Account.getUsers()[index].getScore() + 10);
                                }
                                flag = false;
                            } else if (enqueue(guess.charAt(0)) != 1) {
                                Tool.clearScreen();
                                System.out.println(Colors.RED_BRIGHT + "Your input has already been guessed, please try again" + Colors.RESET);
                                Thread.sleep(3000);
                            } else if (trueFalse(guess.charAt(0)))
                                flag1 = false;
                            else {
                                gallowsPlus();
                                updateXV();
                                if (counter == 14) {
                                    flag = false;
                                }
                            }
                        }
                        if (flag && flag2) {
                            updateDashWord(guess);
                        }
                    }
                    Tool.clearScreen();
                    if (counter == 14)
                        System.out.println(Colors.WHITE_BOLD_BRIGHT + Colors.RED_BACKGROUND + word + Colors.RESET);
                    else
                        System.out.println(Colors.WHITE_BOLD_BRIGHT + Colors.GREEN_BACKGROUND + dashWord + Colors.RESET);
                    printGallows();
                    System.out.println();
                    printXV();
                    System.out.println();
                    if (counter == 14) {
                        Thread.sleep(2750);
                        Tool.clearScreen();
                        System.out.println(Colors.RED_BOLD_BRIGHT + "You die:(" + Colors.RESET);
                        Thread.sleep(3000);
                    } else {
                        assert guess != null;
                        if (!guess.equals("0")) {
                            Account.scorePlus(index);
                            Thread.sleep(2750);
                            Tool.clearScreen();
                            System.out.println(Colors.GREEN_BOLD_BRIGHT + "You win:)" + Colors.RESET);
                            Thread.sleep(3000);
                        }
                    }
                    ignore = new char[0];
                    counter = 0;
                    Account.setHelp(true);
                }
                case "2" -> index = leaderBoard(index);
                case "0" -> index = kingKey = SignInUp.process();
                default -> Tool.invalid();
            }
        }
        Tool.clearScreen();
        Account.sort(index);
        CreateFile.createFile();
        WriteFile.writeFile();
    }
}

class SignInUp {
    public static int process() throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int key = -1;
        while (key < 0) {
            Tool.clearScreen();
            Hangman.printMenu();
            String n = in.next();
            if (n.equals("1")) {
                signUp();
            } else if (n.equals("2") && Account.getUsersLength() == 0) {
                Tool.clearScreen();
                System.out.println(Colors.RED_BRIGHT + "You did not signup, please signup first" + Colors.RESET);
                Thread.sleep(3000);
                Tool.clearScreen();
            } else if (n.equals("2")) {
                key = signIn();
            } else if (n.equals("0")) {
                Tool.clearScreen();
                Hangman.printGoodBye();
                return -1;
            } else {
                Tool.invalid();
            }
        }
        return key;
    }

    public static void signUp() throws InterruptedException {
        Scanner in = new Scanner(System.in);
        boolean flag = true;
        boolean flag1 = true;
        String username = null;
        String password = null;
        while (flag) {
            Tool.clearScreen();
            System.out.println(Colors.YELLOW_BRIGHT + "Please enter your username: (Press 0 to back)" + Colors.RESET);
            String str = in.next();
            if (str.equals("0")) {
                return;
            } else if (Account.chekUsername(str) == -2) {
                Tool.clearScreen();
                System.out.println(Colors.RED_BRIGHT + "Your username has already been registered, please try again" + Colors.RESET);
                Thread.sleep(3000);
                Tool.clearScreen();
            } else {
                username = str;
                flag = false;
            }
        }
        while (flag1) {
            Tool.clearScreen();
            String str = PasswordField.readPassword(Colors.YELLOW_BRIGHT + "Please enter your password: (Press 0 to back)" + Colors.RESET);
//            System.out.println("Password entered was:" + str);
//            System.out.println(Colors.YELLOW_BRIGHT + "Please enter your password: (Press 0 to back)" + Colors.RESET);
//            String str = in.next();
            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            if (str.equals("0")) {
                return;
            } else if (matcher.matches()) {
                password = str;
                flag1 = false;
            } else {
                Tool.clearScreen();
                System.out.println(Colors.RED_BRIGHT + "Your password is invalid, please try again" + Colors.RESET);
                Thread.sleep(3000);
                Tool.clearScreen();
            }
        }
        Account.enqueue(username, password);
        Tool.clearScreen();
        System.out.println(Colors.GREEN_BOLD_BRIGHT + "Your signup is success" + Colors.RESET);
        Thread.sleep(3000);
        Tool.clearScreen();
    }

    public static int signIn() throws InterruptedException {
        Scanner in = new Scanner(System.in);
        int index = -1;
        boolean flag = true;
        while (flag) {
            Tool.clearScreen();
            System.out.println(Colors.YELLOW_BRIGHT + "Please enter your username: (Press 0 to back)" + Colors.RESET);
            String str = in.next();
            int matchUsername = Account.matchUsername(str);
            if (str.equals("0")) {
                return -1;
            } else if (matchUsername == -1) {
                Tool.clearScreen();
                System.out.println(Colors.RED_BRIGHT + "Your username could not be found, please try again" + Colors.RESET);
                Thread.sleep(3000);
                Tool.clearScreen();
            } else {
                index = matchUsername;
                flag = false;
            }
        }
        while (true) {
            Tool.clearScreen();
            String str = PasswordField.readPassword(Colors.YELLOW_BRIGHT + "Please enter your password: (Press 0 to back)" + Colors.RESET);
//            System.out.println("Password entered was:" + str);
//            System.out.println(Colors.YELLOW_BRIGHT + "Please enter your password: (Press 0 to back)" + Colors.RESET);
//            String str = in.next();
            if (str.equals("0")) {
                return -1;
            } else if (Account.chekPassword(str, index)) {
                return index;
            } else {
                Tool.clearScreen();
                System.out.println(Colors.RED_BRIGHT + "Your password does not match, please try again" + Colors.RESET);
                Thread.sleep(3000);
                Tool.clearScreen();
            }
        }
    }
}

class User {
    private String username;
    private String password;
    private int score = 0;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

class Account {
    private static User[] users = new User[0];
    private static boolean help = true;

    public static boolean isHelp() {
        return help;
    }

    public static void setHelp(boolean help) {
        Account.help = help;
    }

    public static User[] getUsers() {
        return users;
    }

    public static int sort(int index) {
        int size = users.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (users[j + 1].getScore() > users[j].getScore()) {
                    if (j == index)
                        index = j + 1;
                    else if (j + 1 == index)
                        index = j;
                    User temp = users[j + 1];
                    users[j + 1] = users[j];
                    users[j] = temp;
                }
            }
        }
        return index;
    }

    public static boolean scoreMinus(int index) throws InterruptedException {
        int target = users[index].getScore();
        if (target < 10 && help) {
            Tool.clearScreen();
            System.out.println(Colors.RED_BRIGHT + "Your score is less than 10" + Colors.RESET);
            Thread.sleep(3000);
            return false;
        }
        if (help) {
            users[index].setScore(target - 10);
            help = false;
            return true;
        }
        Tool.clearScreen();
        System.out.println(Colors.RED_BRIGHT + "You have already used your help once" + Colors.RESET);
        Thread.sleep(3000);
        return false;
    }

    public static void scorePlus(int index) {
        users[index].setScore(users[index].getScore() + 5);
    }

    public static int getUsersLength() {
        return users.length;
    }

    public static int chekUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return -2;
            }
        }
        return -1;
    }

    public static int matchUsername(String username) {
        for (int i = 0; i < users.length; i++) {
            if (users[i].getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    public static boolean chekPassword(String password, int index) {
        return users[index].getPassword().equals(password);
    }

    public static void enqueue(String username, String password) {
        User[] users1 = new User[users.length + 1];
        System.arraycopy(users, 0, users1, 0, users.length);
        users1[users1.length - 1] = new User();
        users1[users1.length - 1].setUsername(username);
        users1[users1.length - 1].setPassword(password);
        users = users1;
    }
}

class Tool {
    static String[] words = {"tehran", "pizza", "banana", "new york", "advanced programming", "michael jordan",
            "lionel messi", "apple", "macaroni", "university", "intel", "kitten", "python", "java",
            "data structures", "algorithm", "assembly", "basketball", "hockey", "leader", "javascript",
            "toronto", "united states of america", "psychology", "chemistry", "breaking bad", "physics",
            "abstract classes", "linux kernel", "january", "march", "time travel", "twitter", "instagram",
            "dog breeds", "strawberry", "snow", "game of thrones", "batman", "ronaldo", "soccer",
            "hamburger", "italy", "greece", "albert einstein", "hangman", "clubhouse", "call of duty",
            "science", "theory of languages and automata"};

    public static String randomWord() {
        return words[(int) (Math.random() * 50)];
    }

    public static void invalid() throws InterruptedException {
        clearScreen();
        System.out.println(Colors.RED_BRIGHT + "Your input is invalid, please try again" + Colors.RESET);
        Thread.sleep(3000);
        clearScreen();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

class PasswordField {
    public static String readPassword(String prompt) {
        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String password = "";
        try {
            password = in.readLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        et.stopMasking();
        return password;
    }
}

class EraserThread implements Runnable {
    private boolean stop;

    public EraserThread(String prompt) {
        System.out.print(prompt);
    }

    public void run() {
        System.out.println();
        while (!stop) {
            System.out.print("\010*");
            try {
                Thread.currentThread().sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public void stopMasking() {
        this.stop = true;
    }
}

class CreateFile {
    public static void createFile() {
        try {
            File myObj = new File("Users.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

class WriteFile {
    public static void writeFile() {
        User[] users = Account.getUsers();
        try {
            FileWriter myWriter = new FileWriter("Users.txt");
            for (User user : users) {
                myWriter.write("{ Username: " + user.getUsername() + ", Password: " + user.getPassword() + ", Score: " + user.getScore() + " }\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

class Colors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
}
