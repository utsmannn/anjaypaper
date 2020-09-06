# Anjaypaper
### The sample of MVI pattern, combine local with api data, PocketDb, and Paging 3

### Feature List
- Paging with Paging 3 Library
- Local first data
- Bookmarking simple toggle
- Handling offline

### Tech stack and 3rd library
- Model-view-viewmodel ([docs](https://developer.android.com/jetpack/guide?gclid=EAIaIQobChMIpPaHkNjT6wIVWw4rCh18ngbJEAAYASAAEgJZWPD_BwE&gclsrc=aw.ds))
- Model-view-intent
- Retrofit ([docs](https://square.github.io/retrofit/))
- Paging 3 ([docs](https://developer.android.com/topic/libraries/architecture/paging/v3-overview))
- PocketDb ([docs](https://github.com/utsmannn/pocketdb))
- Kotlin Coroutine ([docs](https://kotlinlang.org/docs/reference/coroutines-overview.html))
- Glide ([docs](https://github.com/bumptech/glide))
- Koin ([docs](https://insert-koin.io/))

### Setup key
Store your unsplash api key and unsplash base url in `local.properties` like this
```properties
sdk.dir=/Users/utsman/Library/Android/sdk

// setup
unsplash.url=https://api.unsplash.com/
unsplash.client=your-client-api-key
```

### Flow
![](https://i.ibb.co/JkD4hKB/Untitled-Diagram-2.png)

---
```
Copyright 2020 Muhammad Utsman

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```