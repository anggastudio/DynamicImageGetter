<p align="center">
  <h1 align="center">DynamicImageGetter</h1>
  <h4 align="center">Android Library to load image from url inside img tag and adjust the image size.</h4>
</p>

<p align="center">
  <img src="https://images.unsplash.com/photo-1522219406764-db207f1f7640?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"/>
</p>

<p align="center">
  <a href="https://jitpack.io/#anggastudio/DynamicImageGetter"><img src="https://img.shields.io/jitpack/v/github/anggastudio/DynamicImageGetter"></a>
  <a href="LICENSE"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"></a>
  <a href="https://github.com/anggastudio/DynamicImageGetter/pulls"><img alt="Pull request" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat"></a>
  <a href="https://github.com/anggastudio/DynamicImageGetter/graphs/contributors"><img src="https://img.shields.io/github/contributors/anggastudio/DynamicImageGetter"></a>
  <a href="https://twitter.com/angga_studio"><img alt="Twitter" src="https://img.shields.io/twitter/follow/angga_studio"></a>
  <a href="https://github.com/anggastudio"><img alt="Github" src="https://img.shields.io/github/followers/anggastudio?label=follow&style=social"></a>
</p>

## Thanks to:
[rajeefmk](https://gist.github.com/rajeefmk) for his [PicassoImageGetter](https://gist.github.com/rajeefmk/beb1b79363c12041da7fd540bcf27765).
I got the idea to retrieve the image from html using [Picasso](http://square.github.io/picasso/ "Picasso") and put it inside TextView in Android Studio. Then I added some code to adjust the image size dynamically.

## Feature
- Load remote urls from `<img>` tag when loaded inside android `<TextView/>`
- Adjust the image size to :
  - as tall as line height (auto width)
  - as wide as `<TextView/>` width with padding (auto height)
  - or as is (without size adjustment)

## Built With

* [Picasso](http://square.github.io/picasso/ "Picasso") - A powerful image downloading and caching library for Android

## Contributing

Forget it. Just take the code, modify it, and make your own.
But do not forget to thank [rajeefmk](https://gist.github.com/rajeefmk).

## License

What licence?

## Download
#### Gradle
**Step 1.** Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
- cek jitpack version to make sure you use the latest
[![](https://jitpack.io/v/anggastudio/DynamicImageGetter.svg)](https://jitpack.io/#anggastudio/DynamicImageGetter)
```gradle
dependencies {
  implementation 'com.github.anggastudio:DynamicImageGetter:2.0.0'
}
```
#### Maven
**Step 1.**
```xml
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

**Step 2.** Add the dependency
```xml
<dependency>
  <groupId>com.github.anggastudio</groupId>
  <artifactId>DynamicImageGetter</artifactId>
  <version>1.0</version>
</dependency>
```

## Others Configuration
- Make sure to use java 8+ configuration.
- from release 2.0.0 androidX is used

## Usage
- Load image with size as tall as line height (auto width)
```java
        String htmlString = DummyTextHtml.htmlString;
        TextView textView = findViewById(R.id.textView);
        DynamicImageGetter.with(this)
                .load(htmlString)
                .mode(DynamicImageGetter.INLINE_TEXT)
                .into(textView);
```
- Load image with size as wide as `<TextView/>` width with padding (auto height)
```java
        String htmlString = DummyTextHtml.htmlString;
        TextView textView = findViewById(R.id.textView);
        DynamicImageGetter.with(this)
                .load(htmlString)
                .mode(DynamicImageGetter.FULL_WIDTH)
                .into(textView);
```
- Load image with size or as is (without size adjustment)
```java
        String htmlString = DummyTextHtml.htmlString;
        TextView textView = findViewById(R.id.textView);
        DynamicImageGetter.with(this)
                .load(htmlString)
                .into(textView);
```
