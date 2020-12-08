# YumemiSample

Sample Android application written in Kotlin.

## App Usage

- MainActivityにcontributorの一覧が表示されます
- 各contributorの名前をクリックすると詳細情報を示すダイアログを表示します

## Appeal Points

- HTTPリクエストの実装は`fuel`ライブラリを使用して簡潔に
- Activityの再生成時にもデータを保存・復元する
- 一覧表示だけでは名前だけ示して、詳細データはダイアログで表示
- DialogFragmentの再生成も考慮してデータをBundleで渡し保存
