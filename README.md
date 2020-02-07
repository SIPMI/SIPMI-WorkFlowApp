This is your new Play application
=====================================

This file will be packaged with your application, when using `play dist`.

## 動作要件
#### ミドルウェア

1. java 1.8.0
2. Play Framework 2.2.2 (built with Scala 2.10.3)
3. MySQL 5.7

## インストレーション
#### Ubuntu 18.04.2
##### JDK 8 のインストール
`apt install openjdk-8-jdk`

##### playフレームワークのインストール
PlayFramework スタンドアロンパッケージをダウンロードします。

    wget https://downloads.typesafe.com/play/2.2.2/play-2.2.2.zip
    unzip play-2.2.2.zip
    mv play-2.2.2 /usr/local/

playに対してパスを通します。  

    PATH=$PATH:/usr/local/play-2.2.2/
    export PATH

##### MySQLのインストール
MySQLのインストールをします。

    apt install mysql-server



