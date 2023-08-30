package main

import (
	"fmt"
	"strings"
)

func caesarCipher(text string, shift int) string {
	var result strings.Builder
	for _, char := range text {
		if char >= 'a' && char <= 'z' {
			encrypted := 'a' + (char-'a'+rune(shift))%26
			result.WriteRune(encrypted)
		} else if char >= 'A' && char <= 'Z' {
			encrypted := 'A' + (char-'A'+rune(shift))%26
			result.WriteRune(encrypted)
		} else {
			result.WriteRune(char)
		}
	}
	return result.String()
}

func main() {
	var choice, shift int
	var plaintext, ciphertext string
	fmt.Println("Select an option:")
	fmt.Println("1. Encrypt")
	fmt.Println("2. Decrypt")
	fmt.Print("Enter the choice: ")
	fmt.Scan(&choice)
	fmt.Print("Enter the no of shift: ")
	fmt.Scan(&shift)
	switch choice {
	case 1:
		fmt.Print("Enter the plain text: ")
		fmt.Scan(&plaintext)
		ciphertext = caesarCipher(plaintext, shift)
		fmt.Println("Cipher Text:", ciphertext)
	case 2:
		fmt.Print("Enter the cipher text: ")
		fmt.Scan(&ciphertext)
		plaintext = caesarCipher(ciphertext, 26-shift)
		fmt.Println("Plain Text:", plaintext)
	default:
		fmt.Print("Invalid choice!")
	}
}
