# Caesar Cipher

The Caesar Cipher is one of the simplest and most widely known encryption techniques. It is a type of substitution cipher in which each letter in the plaintext is shifted a certain number of places down the alphabet.

## Working

The Caesar Cipher algorithm works by taking a message (plaintext) and a key (the number of positions to shift each letter), and replacing each letter in the plaintext with a letter that is key positions down the alphabet. For example, with a key of 3, the letter A would be replaced by D, B would become E, and so on.

To decrypt the message, you simply shift each letter back key positions in the alphabet.

## Implementation

The Caesar Cipher can be implemented in any programming language that supports string manipulation. Here's a implementation in Go programming language:

```go
func encrypt(plaintext string, shift int) string {
    var ciphertext string
    for _, c := range plaintext {
        if c >= 'a' && c <= 'z' {
            ciphertext += string('a' + (c - 'a' + rune(shift)) % 26)
        } else if c >= 'A' && c <= 'Z' {
            ciphertext += string('A' + (c - 'A' + rune(shift)) % 26)
        } else {
            ciphertext += string(c)
        }
    }
    return ciphertext
}

func decrypt(ciphertext string, shift int) string {
    return encrypt(ciphertext, 26 - shift)
}
```

In this implementation, we iterate over each letter in the message string and shift it by key positions down the alphabet. We handle upper and lowercase letters separately, and we do not shift non-letter characters.

## Conclusion

The Caesar Cipher is a simple encryption technique that is easy to implement and understand. However, it is not very secure, as an attacker can easily break the cipher using brute force. Nonetheless, it is still used in some applications today, such as ROT13 (which is a Caesar Cipher with a key of 13).