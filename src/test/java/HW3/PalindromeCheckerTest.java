package HW3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PalindromeCheckerTest {

    private static PalindromeChecker checker;


    String firstCase = "abba";
    String secondCase = "aba";
    String thirdCase = "a  aab a aa";
    String fourthCase = "a  aab a a";
    String fifthCase = "abbc";
    String sixthCase = "Ab bB a";

    @BeforeAll
    static void initChecker(){
        checker = new PalindromeChecker();
    }


    @Test
    @DisplayName("Тест с данными: \"abba\"")

    void firstCaseTest(){
        Assertions.assertTrue(checker.isPalindrome(firstCase));
    }

    @Test
    @DisplayName("Тест с данными: \"aba\"")
    void secondCaseTest(){
        Assertions.assertTrue(checker.isPalindrome(secondCase));
    }

    @Test
    @DisplayName("Тест с данными: \"a  aab a aa\"")
    void thirdCaseTest(){
        Assertions.assertTrue(checker.isPalindrome(thirdCase));
    }

    @Test
    @DisplayName("Тест с данными: \"a  aab a a\"")
    void fourthCaseTest(){
        Assertions.assertTrue(checker.isPalindrome(fourthCase));
    }

    @Test
    @DisplayName("Тест с данными: \"abbc\"")
    void fifthCaseTest(){
        Assertions.assertTrue(checker.isPalindrome(fifthCase));
    }

    @Test
    @DisplayName("Тест с данными: \"Ab bB a\"")
    void sixthCaseTest(){
        Assertions.assertTrue(checker.isPalindrome(sixthCase));
    }


}
