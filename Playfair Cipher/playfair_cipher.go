package main

import (
	"fmt"
	"strings"
)

func createPlayfairMatrix(key string) [5][5]string {
	key = strings.ToUpper(strings.ReplaceAll(key, " ", ""))
	seenChars := make(map[rune]bool)
	var keyChars []rune
	for _, char := range key {
		if !seenChars[char] {
			seenChars[char] = true
			keyChars = append(keyChars, char)
		}
	}

	for char := 'A'; char <= 'Z'; char++ {
		if char != 'J' && !seenChars[char] {
			keyChars = append(keyChars, char) // add remaining alphabet to key
		}
	}

	matrix := [5][5]string{}
	for i, char := range keyChars {
		matrix[i/5][i%5] = string(char)
	}
	return matrix
}

func findPosition(matrix [5][5]string, letter string) (int, int) {
	for i := 0; i < 5; i++ {
		for j := 0; j < 5; j++ {
			if matrix[i][j] == letter {
				return i, j
			}
		}
	}
	return -1, -1
}

func playfairEncrypt(plaintext, key string) string {
	matrix := createPlayfairMatrix(key)
	plaintext = strings.ToUpper(strings.ReplaceAll(plaintext, " ", ""))
	plaintext = strings.ReplaceAll(plaintext, "J", "I") // replace any 'J' with 'I'

	// insert 'X' between consecutive identical letters
	for i := 0; i < len(plaintext)-1; i += 2 {
		if plaintext[i] == plaintext[i+1] {
			plaintext = plaintext[:i+1] + "X" + plaintext[i+1:]
		}
	}

	// add 'X' to the end of plaintext if it has odd length
	if len(plaintext)%2 == 1 {
		plaintext += "X"
	}

	// encrypt pairs of letters using the matrix
	ciphertext := ""
	for i := 0; i < len(plaintext); i += 2 {
		x1, y1 := findPosition(matrix, string(plaintext[i]))
		x2, y2 := findPosition(matrix, string(plaintext[i+1]))
		if x1 == x2 {
			ciphertext += matrix[x1][(y1+1)%5] + matrix[x2][(y2+1)%5]
		} else if y1 == y2 {
			ciphertext += matrix[(x1+1)%5][y1] + matrix[(x2+1)%5][y2]
		} else {
			ciphertext += matrix[x1][y2] + matrix[x2][y1]
		}
	}
	return ciphertext
}

func playfairDecrypt(ciphertext, key string) string {
	matrix := createPlayfairMatrix(key)
	ciphertext = strings.ToUpper(strings.ReplaceAll(ciphertext, " ", ""))

	// replace any 'J' with 'I'
	ciphertext = strings.ReplaceAll(ciphertext, "J", "I")

	// decrypt pairs of letters using the matrix
	plaintext := ""
	for i := 0; i < len(ciphertext); i += 2 {
		x1, y1 := findPosition(matrix, string(ciphertext[i]))
		x2, y2 := findPosition(matrix, string(ciphertext[i+1]))
		if x1 == x2 {
			plaintext += matrix[x1][(y1+4)%5] + matrix[x2][(y2+4)%5]
		} else if y1 == y2 {
			plaintext += matrix[(x1+4)%5][y1] + matrix[(x2+4)%5][y2]
		} else {
			plaintext += matrix[x1][y2] + matrix[x2][y1]
		}
	}

	// remove any 'X' that was added as padding
	plaintext = strings.ReplaceAll(plaintext, "X", "")
	return plaintext
}

func main() {
	var choice int
	var key, plaintext, ciphertext string

	fmt.Println("Select an option:")
	fmt.Println("1. Encrypt")
	fmt.Println("2. Decrypt")
	fmt.Print("Enter the choice: ")
	fmt.Scanln(&choice)

	fmt.Println("Enter the key:")
	fmt.Scanln(&key)

	switch choice {
	case 1:
		fmt.Println("Enter the plaintext:")
		fmt.Scanln(&plaintext)
		ciphertext = playfairEncrypt(plaintext, key)
		fmt.Println("Ciphertext:", ciphertext)
	case 2:
		fmt.Println("Enter the ciphertext:")
		fmt.Scanln(&ciphertext)
		plaintext = playfairDecrypt(ciphertext, key)
		fmt.Println("Plaintext:", plaintext)
	default:
		fmt.Println("Invalid choice")
	}
}
