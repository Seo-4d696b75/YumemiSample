# YumemiSample

Sample Android application written in Kotlin.

## App Usage

- MainActivityにcontributorの一覧が表示されます
- 各contributorの名前をクリックすると詳細情報を示すダイアログを表示します

## Appeal Points

- Jetpack を利用したモダンで簡潔な設計
    - DataRepository アプリで使用するデータ（contributor一覧）の取得・保持を管理する
    - *ViewModel 各Activity, Fragmentで必要になるデータの取得・処理を担う
    - Activity, Fragment はViewを適宜更新してUIの操作に徹底
