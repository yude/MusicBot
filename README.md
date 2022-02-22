# このフォークについて
このフォークは、オリジナルの [JMusicBot](https://github.com/jagrosh/MusicBot) に、以下の変更が加えられています。
* Docker のセットアップ
* 日本語化
## インストール
1. サーバー上に適当なディレクトリを作成します。
2. `data/config.txt` に以下の内容をペーストし、必要な項目を入力します。
```
/////////////////////////////////////////////////////////
// Config for the JMusicBot                            //
/////////////////////////////////////////////////////////
// Any line starting with // is ignored                //
// You MUST set the token and owner                    //
// All other items have defaults if you don't set them //
// Open in Notepad++ for best results                  //
/////////////////////////////////////////////////////////


// This sets the token for the bot to log in with
// This MUST be a bot token (user tokens will not work)
// If you don't know how to get a bot token, please see the guide here:
// https://github.com/jagrosh/MusicBot/wiki/Getting-a-Bot-Token

token = 


// This sets the owner of the bot
// This needs to be the owner's ID (a 17-18 digit number)
// https://github.com/jagrosh/MusicBot/wiki/Finding-Your-User-ID

owner = 


// This sets the prefix for the bot
// The prefix is used to control the commands
// If you use !!, the play command will be !!play
// If you do not set this, the prefix will be a mention of the bot (@Botname play)

prefix = "."


// If you set this, it modifies the default game of the bot
// Set this to NONE to have no game
// Set this to DEFAULT to use the default game
// You can make the game "Playing X", "Listening to X", or "Watching X"
// where X is the title. If you don't include an action, it will use the
// default of "Playing"

game = "DEFAULT"


// If you set this, it will modify the default status of bot
// Valid values: ONLINE IDLE DND INVISIBLE

status = ONLINE


// If you set this to true, the bot will list the title of the song it is currently playing in its
// "Playing" status. Note that this will ONLY work if the bot is playing music on ONE guild;
// if the bot is playing on multiple guilds, this will not work.

songinstatus=false


// If you set this, the bot will also use this prefix in addition to
// the one provided above

altprefix = "NONE"


// If you set these, it will change the various emojis

success = "🎶"
warning = "💡"
error = "🚫"
loading = "⌚"
searching = "🔎"


// If you set this, you change the word used to view the help.
// For example, if you set the prefix to !! and the help to cmds, you would type
// !!cmds to see the help text

help = help


// If you set this, the "nowplaying" command will show youtube thumbnails
// Note: If you set this to true, the nowplaying boxes will NOT refresh
// This is because refreshing the boxes causes the image to be reloaded
// every time it refreshes.

npimages = true


// If you set this, the bot will not leave a voice channel after it finishes a queue.
// Keep in mind that being connected to a voice channel uses additional bandwith,
// so this option is not recommended if bandwidth is a concern.

stayinchannel = true


// This sets the maximum amount of seconds any track loaded can be. If not set or set
// to any number less than or equal to zero, there is no maximum time length. This time
// restriction applies to songs loaded from any source.

maxtime = 0

// This sets the amount of seconds the bot will stay alone on a voice channel until it
// automatically leaves the voice channel and clears the queue. If not set or set
// to any number less than or equal to zero, the bot won't leave when alone.

alonetimeuntilstop = 0

// This sets an alternative folder to be used as the Playlists folder
// This can be a relative or absolute path

playlistsfolder = "Playlists"


// By default, the bot will DM the owner if the bot is running and a new version of the bot
// becomes available. Set this to false to disable this feature.

updatealerts=false


// Changing this changes the lyrics provider
// Currently available providers: "A-Z Lyrics", "Genius", "MusicMatch", "LyricsFreak"
// At the time of writing, I would recommend sticking with A-Z Lyrics or MusicMatch,
// as Genius tends to have a lot of non-song results and you might get something 
// completely unrelated to what you want.
// If you are interested in contributing a provider, please see
// https://github.com/jagrosh/JLyrics

lyrics.default = "MusixMatch"


// These settings allow you to configure custom aliases for all commands.
// Multiple aliases may be given, separated by commas.
//
// Example 1: Giving command "play" the alias "p":
// play = [ p ]
//
// Example 2: Giving command "search" the aliases "yts" and "find":
// search = [ yts, find ]

aliases {
  // General commands
  settings = [ status ]

  // Music commands
  lyrics = []
  nowplaying = [ np, current ]
  play = []
  playlists = [ pls ]
  queue = [ list ]
  remove = [ delete ]
  scsearch = []
  search = [ ytsearch ]
  shuffle = []
  skip = [ voteskip ]

  // Admin commands
  prefix = [ setprefix ]
  setdj = []
  settc = []
  setvc = []

  // DJ Commands
  forceremove = [ forcedelete, modremove, moddelete ]
  forceskip = [ modskip ]
  movetrack = [ move ]
  pause = []
  playnext = []
  repeat = []
  skipto = [ jumpto ]
  stop = []
  volume = [ vol ]
}

// If you set this to true, it will enable the eval command for the bot owner. This command
// allows the bot owner to run arbitrary code from the bot's account.
//
// WARNING:
// This command can be extremely dangerous. If you don't know what you're doing, you could
// cause horrific problems on your Discord server or on whatever computer this bot is running
// on. Never run this command unless you are completely positive what you are running.
//
// DO NOT ENABLE THIS IF YOU DON'T KNOW WHAT THIS DOES OR HOW TO USE IT
// IF SOMEONE ASKS YOU TO ENABLE THIS, THERE IS AN 11/10 CHANCE THEY ARE TRYING TO SCAM YOU

eval=false
```
3. `docker-compose.yaml` に以下の内容をペーストします。
```yaml
version: '3'
services:
  app:
    restart: always
    build: https://github.com/yude/MusicBot.git
    volumes:
      - "./data:/app"
```
* 自分のコンピュータで Docker イメージをビルドしたくない場合、ビルド済みのイメージを利用することができます。
  * 現在、`amd64` アーキテクチャ向けイメージのみ提供しています。
* ビルド済みのイメージを利用する場合、上の YAML 記述の代わりに以下を利用してください。
```yaml
version: '3'
services:
  app:
    restart: always
    image: ghcr.io/yude/musicbot:master
    volumes:
      - "./data:/app"
```
4. `docker-compose up -d` を実行します。

## アップデート
* このフォーク リポジトリの更新を適用するには、以下の手順を踏んでください。
1. `docker-compose build` を実行します。
2. `docker-compose up -d --remove-orphans` を実行します。

<img align="right" src="https://i.imgur.com/zrE80HY.png" height="200" width="200">

# JMusicBot

[![Downloads](https://img.shields.io/github/downloads/jagrosh/MusicBot/total.svg)](https://github.com/jagrosh/MusicBot/releases/latest)
[![Stars](https://img.shields.io/github/stars/jagrosh/MusicBot.svg)](https://github.com/jagrosh/MusicBot/stargazers)
[![Release](https://img.shields.io/github/release/jagrosh/MusicBot.svg)](https://github.com/jagrosh/MusicBot/releases/latest)
[![License](https://img.shields.io/github/license/jagrosh/MusicBot.svg)](https://github.com/jagrosh/MusicBot/blob/master/LICENSE)
[![Discord](https://discordapp.com/api/guilds/147698382092238848/widget.png)](https://discord.gg/0p9LSGoRLu6Pet0k)<br>
[![CircleCI](https://img.shields.io/circleci/project/github/jagrosh/MusicBot/master.svg)](https://circleci.com/gh/jagrosh/MusicBot)
[![AppVeyor](https://ci.appveyor.com/api/projects/status/gdu6nyte5psj6xfk/branch/master?svg=true)](https://ci.appveyor.com/project/jagrosh/musicbot/branch/master)
[![CodeFactor](https://www.codefactor.io/repository/github/jagrosh/musicbot/badge)](https://www.codefactor.io/repository/github/jagrosh/musicbot)

A cross-platform Discord music bot with a clean interface, and that is easy to set up and run yourself!

[![Setup](http://i.imgur.com/VvXYp5j.png)](https://jmusicbot.com/setup)

## Features
  * Easy to run (just make sure Java is installed, and run!)
  * Fast loading of songs
  * No external keys needed (besides a Discord Bot token)
  * Smooth playback
  * Server-specific setup for the "DJ" role that can moderate the music
  * Clean and beautiful menus
  * Supports many sites, including Youtube, Soundcloud, and more
  * Supports many online radio/streams
  * Supports local files
  * Playlist support (both web/youtube, and local)

## Supported sources and formats
JMusicBot supports all sources and formats supported by [lavaplayer](https://github.com/sedmelluq/lavaplayer#supported-formats):
### Sources
  * YouTube
  * SoundCloud
  * Bandcamp
  * Vimeo
  * Twitch streams
  * Local files
  * HTTP URLs
### Formats
  * MP3
  * FLAC
  * WAV
  * Matroska/WebM (AAC, Opus or Vorbis codecs)
  * MP4/M4A (AAC codec)
  * OGG streams (Opus, Vorbis and FLAC codecs)
  * AAC streams
  * Stream playlists (M3U and PLS)

## Example
![Loading Example...](https://i.imgur.com/kVtTKvS.gif)

## Setup
Please see the [Setup Page](https://jmusicbot.com/setup) to run this bot yourself!

## Questions/Suggestions/Bug Reports
**Please read the [Issues List](https://github.com/jagrosh/MusicBot/issues) before suggesting a feature**. If you have a question, need troubleshooting help, or want to brainstorm a new feature, please start a [Discussion](https://github.com/jagrosh/MusicBot/discussions). If you'd like to suggest a feature or report a reproducible bug, please open an [Issue](https://github.com/jagrosh/MusicBot/issues) on this repository. If you like this bot, be sure to add a star to the libraries that make this possible: [**JDA**](https://github.com/DV8FromTheWorld/JDA) and [**lavaplayer**](https://github.com/sedmelluq/lavaplayer)!

## Editing
This bot (and the source code here) might not be easy to edit for inexperienced programmers. The main purpose of having the source public is to show the capabilities of the libraries, to allow others to understand how the bot works, and to allow those knowledgeable about java, JDA, and Discord bot development to contribute. There are many requirements and dependencies required to edit and compile it, and there will not be support provided for people looking to make changes on their own. Instead, consider making a feature request (see the above section). If you choose to make edits, please do so in accordance with the Apache 2.0 License.
