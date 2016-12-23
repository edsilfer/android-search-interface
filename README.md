Android Search Interface
===

<a name="version"></a>[ ![Download](https://api.bintray.com/packages/edsilfer/maven/search-interface/images/download.svg) ](https://bintray.com/edsilfer/maven/search-interface/_latestVersion) - **MIN API VERSION: 16**

Introduction
-
While developing an application, a very common functionality is to perform searches - either, locally or remotelly - and let the user to select between a set of items. To achive this feature it is required, at least, a `Fragment` or `Activity` containing a `RecyclerView`, different row layouts, `RecyclerviewAdapte`r and its `ViewHolders`, all of that if the developer applies reusability very well. In order to ensure code orthogonality, Android Search Interface comes to offer the developer with a common but flexible interface to create Search/Select Business Streams.

<a name="showcase"></a>**SHOWCASE**

<p align="center">
  <img src="art/showcase.gif" align="center" width=150>
  <img src="art/ss_sample_01.png" align="center" width=150>
  <img src="art/ss_template_01_circle_result_row.png" align="center" width=150>
  <img src="art/ss_template_02_circle_result_row.png" align="center" width=150>
  <img src="art/ss_template_03_circle_result_row.png" align="center" width=150>
  <br /><br />
  <i><b>Figure 01:</b> sample searches provided through presets or custom styles</i>
</p>

How does it work?
-
Android List Manager was developed using [Kotlin language](https://kotlinlang.org/). Kotlin is free to use and owned by [Jet Brains](https://www.jetbrains.com/). It adds a lot of cool features, boosting your productiveness while keeping everythying **100% compatible with Java.** 

_For details about technical implementation of this library please refer to the source code._

- <a name="step1">**Step 01: import the module**

Add the library module dependency to your project:
```groovy
compile 'br.com.edsilfer.android:search-interface:1.0.0'
```

_Please refer to the [badge](#version) of the page to get the latest version of this library._

- <a name="step2">**Step 02: make your POJO class extends `IResultRow`**

The model class whose items you intend to display as search result must implement the interface below:

```kotlin
interface IResultRow : Serializable {

    fun getThumbnail(): String

    fun getHeader(): String

    fun getSubHeader1(): String

    fun getSubHeader2(): String

}

```

- <a name="step3">**Step 03: subscribe for search events**
```kotlin
 NotificationCenter.subscribe(Events.ON_SEARCH_TYPED, object : ISubscriber {
            override fun execute(event: Events, payload: Any) {
            }
        }))

 NotificationCenter.subscribe(Events.ITEM_CHOSEN, object : ISubscriber {
            override fun execute(event: Events, payload: Any) {
            }
        })
```
- Event  `Events.ON_SEARCH_TYPED` is called whenever user types a letter on input area. In this case, `payload` maps to a String with the query;
- Event  `Events.ITEM_CHOSEN` is called when user selects a result. In this case, `payload` maps to the type of the returned set of items (see (step 4)[#step4]);

- <a name="step4">**Step 04: perform the search and update the results**

After performing the search, update the results like shown in the snippet below:

```kotlin
NotificationCenter.notify(Events.UPDATE_RESULTS, RESULT_LIST)
```
_Where `RESULT_LIST` is any POJO that implements the interface `IResultRow`_

- <a name="step5">**Step 05: call the search activity**

```kotlin
val intent = Intent(this, ActivitySearch::class.java)
intent.putExtra(ActivitySearch.ARG_SEARCH_TEMPLATE, SEARCH_ACTIVITY_PALLET)
startActivity(intent)
```
Where `SEARCH_ACTIVITY_PALLET` is descendent of `SearchPallet`, which has all customizable attributes (see [step 6](#step6))

- <a name="step6">**Step 06: customize your activity**

_Tutorial comming soon, meanwhile refer to the [sample app](https://github.com/edsilfer/android-search-interface/blob/master/app/src/main/java/br/com/edsilfer/android/sinterface/demo/presenter/Sample01.kt) for further details on how to customize the search activity_

_**PS.: All code supplied is written in Kotlin and can be used together with Java.**_

Next Steps
-
- Add support for multiple item select;
- Add support for recent search suggestions;
- Add support for custom suggestions;

License
-
Copyright 2016 Edgar da Silva Fernandes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
