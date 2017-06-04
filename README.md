Thex
====
任意のディレクトリ(apacheのDocumentRoot相当)に置かれたコンテンツを返すシンプルなWebサーバ。

##なにもの？
[Thymeleaf](http://www.thymeleaf.org/)習作として書きました。
Webサイトを書く労力を割くため様々なサーバ・エンジンが存在しますが、
なんだかんだで、自分でHTMLを書いた方が早いかな、と思うことが時々あります。
そういう場合、Apache HTTP Serverだけ立てて、せっせと自分でHTMLファイルを書きます。
しかし、素のHTMLによるWebサイトでは、HTMLヘッダ等繰り返し書く部分が面倒で仕方がないです。
せめてincludeが出来ればその部分を部品に出来るのですが・・・
普通ならそういう場合、PHPといったエンジンを使うのでしょうが、Thymeleafを使って実装してみました。
目的はThymeleafのinclude/replaceの利用。fragmentも秀逸だと思います。

##使い方
本作は単純なサーブレットです。
`mvn package`して、作ったwarを適当にデプロイしてください。
自分はもっぱらTomcatを使ってデバッグしています。
起動時、Javaパラメータ「thex.base」の値にHTMLファイルを置いたディレクトリを指定してください。

```
例：-Dthex.base=/var/www/html
```
