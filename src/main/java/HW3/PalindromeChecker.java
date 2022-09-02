package HW3;

public class PalindromeChecker {

        public boolean isPalindrome(String word) {
            if (word.trim().length() < 2){
                return true;
            }
            if (word.trim().toLowerCase().charAt(0)
                    != word.trim().toLowerCase().charAt(word.trim().length() - 1)) {
                return false;
            }
            return isPalindrome(word.trim().toLowerCase().substring(1, word.trim().length() - 1));
        }

}
