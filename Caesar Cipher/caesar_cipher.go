package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func encrypt(plaintext string, shift int) string {
	var ciphertext string
	for _, c := range plaintext {
		if c >= 'a' && c <= 'z' {
			ciphertext += string('a' + (c-'a'+rune(shift))%26)
		} else if c >= 'A' && c <= 'Z' {
			ciphertext += string('A' + (c-'A'+rune(shift))%26)
		} else {
			ciphertext += string(c)
		}
	}
	return ciphertext
}

func decrypt(ciphertext string, shift int) string {
	return encrypt(ciphertext, 26-shift)
}

func main() {
	scanner := bufio.NewScanner(os.Stdin)

	fmt.Print("Enter the plain text: ")
	scanner.Scan()
	plaintext := scanner.Text()
	fmt.Print("Enter shift value: ")
	scanner.Scan()
	shift, err := strconv.Atoi(scanner.Text())
	if err != nil {
		fmt.Println("Invalid shift value")
		return
	}

	fmt.Println("Select operation:")
	fmt.Println("1. Encrypt")
	fmt.Println("2. Decrypt")
	fmt.Print("Enter the choice: ")
	scanner.Scan()
	choice, err := strconv.Atoi(scanner.Text())
	if err != nil || (choice != 1 && choice != 2) {
		fmt.Println("Invalid choice")
		return
	}

	var result string
	if choice == 1 {
		result = encrypt(plaintext, shift)
	} else {
		result = decrypt(plaintext, shift)
	}

	fmt.Println(result)
}
