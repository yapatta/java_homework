# java_homework

大学のプログラミング第２同演習の最終課題コンサート予約システムの実装

## 特徴
- SwingでGUI実装
  - すべてのビューを管理するFrame, ルーターみたいなイメージ(MainFrame.java)
  - コンサート一覧画面(ConcertsPanel.java)
  - Myチケット管理画面(MyConcertsPanel.java)
  - ログイン画面(LoginPanel.java)
  - Adminチケット・ユーザ管理画面(AdminPanel.java)
  - Adminコンサート作成画面(CreateConcertPanel.java)
  - Adminユーザー作成画面(CreateUserPanel.java)
- MVCパターンで実装
  - Panel内にViewとControllerが存在
    - Panelを再表示するときにreload関数で必要な情報をModelから取得してViewを表示
    - 状態変化を受け取ったらcolleagueChangedを実行(親に渡す)
  - ModelにはUserReader.java
    - 課題の都合上SQLを利用できなかったためCSVファイルをデータベース代わりにして読み込んでいる
    - 結合とかをするときN+1問題が生じてしまうがデータ数が少ないor時間が限られたため勘弁...
- Mediatorパターンで実装
  - MainFrameが複数の画面(Panel)を持っており, 子からの状態変化(colleagueChange)がMainFrameに通知されると適切な画面(Panel)を表示
