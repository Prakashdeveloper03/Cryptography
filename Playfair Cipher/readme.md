# Playfair Cipher

The Playfair Cipher is a polygraphic substitution cipher that encrypts pairs of letters instead of individual letters. It was invented by Charles Wheatstone in 1854, but was later named after his friend, Baron Playfair, who popularized the cipher.

## Working

The Playfair Cipher algorithm works by first generating a key square, which is a 5x5 grid of letters containing all the letters of the alphabet except for "J". The key phrase is used to generate this key square. The plaintext message is then divided into pairs of letters. If there is an odd number of letters, a dummy letter such as "X" is added to the end of the message. Each pair of letters is then encrypted using the key square.

To encrypt a pair of letters, we first find the positions of each letter in the key square. If the letters are in the same row of the key square, we replace each letter with the letter to its right, wrapping around to the first letter in the row if necessary. If the letters are in the same column of the key square, we replace each letter with the letter below it, wrapping around to the top of the column if necessary. If the letters are not in the same row or column, we replace each letter with the letter in the same row and in the column of the other letter.

To decrypt the message, we simply reverse the process by finding the positions of each pair of letters in the key square and replacing each letter with the letter to its left or above it, as appropriate.

### Implementation

The Playfair Cipher can be implemented in any programming language that supports string manipulation. Here's an implementation in Go programming language:

```go
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
			keyChars = append(keyChars, char)
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
	plaintext = strings.ReplaceAll(plaintext, "J", "I")
	for i := 0; i < len(plaintext)-1; i += 2 {
		if plaintext[i] == plaintext[i+1] {
			plaintext = plaintext[:i+1] + "X" + plaintext[i+1:]
		}
	}
	if len(plaintext)%2 == 1 {
		plaintext += "X"
	}
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
	ciphertext = strings.ReplaceAll(ciphertext, "J", "I")
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
	plaintext = strings.ReplaceAll(plaintext, "X", "")
	return plaintext
}
```

In this implementation, we first generate the key square using the key phrase. We then divide the plaintext message into pairs of letters and encrypt each pair using the key square. We handle edge cases such as repeated letters and odd-length messages. Decryption is handled similarly, but with the inverse transformations.

## Conclusion

The Playfair Cipher is a more complex encryption technique than the Caesar Cipher, but it provides better security as it encrypts pairs of letters instead of individual letters. However, it is still vulnerable to certain types of attacks, such as known plaintext attacks and frequency analysis. Nonetheless, it was widely used in the early 20th century for military and diplomatic communications.