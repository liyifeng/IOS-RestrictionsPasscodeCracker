# IOS-RestrictionsPasscodeCracker
IOS访问限制密码破解-iOS Restrictions Passcode Cracker

###原理：###
四位数字密码+salt 通过PBKDF2WithHmacSHA1算法加密得到密文，从iPhone的备份文件中能够得到salt和密文的BASE64加密版本。
###伪代码：###
1. 从iPhone的备份文件找到RestrictionsPasswordKey和RestrictionsPasswordSalt（在哪里找这两个变量请参考http://crack.0000000.info/）；
2. Base64解密RestrictionsPasswordKey和RestrictionsPasswordSalt 得到新变量key和salt；
3. 暴力穷举0000到9999之间的数字 与salt进行PBKDF2WithHmacSHA1加密得到密文与key进行对比，相同则破解出密码。

###使用方法：###
main方法设置变量值，运行。最迟3秒破解出密码。

作者：liyifeng
