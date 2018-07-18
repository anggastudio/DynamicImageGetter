# DynamicImageGetter
Android Library to load image from url inside `<img>` tag and adjust the image size.

## Thanks to:
[rajeefmk](https://gist.github.com/rajeefmk) for his [PicassoImageGetter](https://gist.github.com/rajeefmk/beb1b79363c12041da7fd540bcf27765).
I get the idea to retrieve the image from html using [Picasso](http://square.github.io/picasso/ "Picasso") and put it inside TextView in Android Studio. Then I added some code to adjust the image size dynamically.

## Feature
- Load remote urls from `<img>` tag when loaded inside android `<TextView/>`
- Adjust the image size to :
-- as tall as line height (auto width)
-- as wide as `<TextView/>` width with padding (auto height)
-- or as is (without size adjustment)

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
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

**Step 2.** Add the dependency
```
dependencies {
  implementation 'com.github.anggastudio:DynamicImageGetter:1.0'
}
```
#### Maven
**Step 1.**
```
<repositories>
  <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
  </repository>
</repositories>
```

**Step 2.** Add the dependency
```
<dependency>
  <groupId>com.github.anggastudio</groupId>
  <artifactId>DynamicImageGetter</artifactId>
  <version>1.0</version>
</dependency>
```

## Usage
- Load image with size as tall as line height (auto width)
```
TextView textHtml = findViewById(R.id.textHtml);
DynamicImageGetter imageGetter = new DynamicImageGetter(getApplicationContext(), textHtml, DynamicImageGetter.INLINE_TEXT);
textHtml.setText(Html.fromHtml(htmlString, imageGetter, null));
```
- Load image with size as wide as `<TextView/>` width with padding (auto height)
```
TextView textHtml = findViewById(R.id.textHtml);
DynamicImageGetter imageGetter = new DynamicImageGetter(getApplicationContext(), textHtml, DynamicImageGetter.FULL_WIDTH);
textHtml.setText(Html.fromHtml(htmlString, imageGetter, null));
```
- Load image with size or as is (without size adjustment)
```
TextView textHtml = findViewById(R.id.textHtml);
DynamicImageGetter imageGetter = new DynamicImageGetter(getApplicationContext(), textHtml);
textHtml.setText(Html.fromHtml(htmlString, imageGetter, null));
```
