# java-bloom-filter-new

There are various situations in which we need to determine whether something belongs to a set, and there are numerous algorithms for doing so. 
A spell checker, for example, looks up whether a term is in the dictionary.

# Approach
* Consider a large array of bits that is initially all zero.
* Then select each word from your word dictionary.
* For each word, generate 'n' different hash values. Set the array's bit locations to the matching hash values.

The MD5 algorithm can be used to find the hash values. The MD5 algorithm generates a 128-bit (16-byte) hash value, 
from which smaller hash values are extracted by extracting sequences of bits.

Sandaka Wijesinghe -
