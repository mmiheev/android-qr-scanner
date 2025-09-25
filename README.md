# QR Scanner Library

<img src="https://img.shields.io/badge/Android-3DDC84?style=flat&logo=android&logoColor=white" />
<img src="https://img.shields.io/badge/Kotlin-0095D5?style=flat&logo=kotlin&logoColor=white" />
[![JitPack](https://jitpack.io/v/mmiheev/android-qr-scanner.svg)](https://jitpack.io/#mmiheev/android-qr-scanner)

A simple and convenient QR code scanning library for Android. Built on CameraX with a ready-to-use Activity for quick integration into your application.

## Features

- Uses CameraX for stable camera performance
- Supports Android 7.0+ (API 24)
- Easy integration

## Installation

### Step 1: Add JitPack Repository

In your `settings.gradle.kts` file add:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add Dependency

In your module's `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.github.mmiheev:android-qr-scanner:v1.0.2")
}
```

### Step 3: Add Activity to AndroidManifest.xml

```xml
<application>
    <!-- Your other activities -->
    
    <activity
        android:name="com.zeon.qrscanner.QrScannerActivity"
        android:exported="false" />
</application>
```
## Complete Implementation Example

<details>
<summary><b>Click to expand implementation examples</b></summary>

### Java
```java
public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Unit> qrScannerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScannerLauncher = registerForActivityResult(
                new QrScanner.ScannerContract(),
                result -> handleQrResult(result)
        );

        Button scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(v -> launchQrScanner());
    }

    private void launchQrScanner() {
        qrScannerLauncher.launch(Unit.INSTANCE);
    }

    private void handleQrResult(QrResult result) {
        if (result instanceof QrResult.Success) {
            String scannedText = ((QrResult.Success) result).getText();
            Toast.makeText(this, "Scanned: " + scannedText, Toast.LENGTH_SHORT).show();
        } else if (result instanceof QrResult.Error) {
            QrException error = ((QrResult.Error) result).getException();
            Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        } else if (result instanceof QrResult.Canceled) {
            Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show();
        }
    }
}
```
### Kotlin
```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var resultTextView: TextView

    private val qrScannerLauncher = registerForActivityResult(QrScanner.ScannerContract()) { result ->
        when (result) {
            is QrResult.Success -> {
                resultTextView.text = "Result: ${result.text}"
                Toast.makeText(this, "QR code recognized: ${result.text}", Toast.LENGTH_SHORT).show()
            }
            is QrResult.Error -> {
                resultTextView.text = "Error: ${result.exception.message}"
                Toast.makeText(this, "Error: ${result.exception.message}", Toast.LENGTH_SHORT).show()
            }
            QrResult.Canceled -> {
                resultTextView.text = "Scan canceled"
                Toast.makeText(this, "Scan canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)
        val scanButton: Button = findViewById(R.id.scanButton)

        scanButton.setOnClickListener {
            qrScannerLauncher.launch(Unit)
        }
    }
}
```
</details>

## Required Permissions

The library automatically requests required permissions:

- `android.permission.CAMERA` - for camera access

## Troubleshooting

### If scanner doesn't launch:
1. Ensure Activity is added to AndroidManifest.xml
2. Verify camera is available on the device

### If QR codes aren't recognized:
1. Ensure camera focuses on the code
2. Check lighting conditions
3. Verify QR code is not damaged

## License

```
MIT License

Copyright (c) 2025 Maxim Miheev

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## Support

If you encounter issues or have questions:
1. Check [Issues](https://github.com/mmiheev/android-qr-scanner/issues) on GitHub
2. Create a new Issue with problem description
3. Specify library version and device model

---

**Version:** v1.0.2  
**Min Android Version:** 7.0 (API 24)  
**Required Permissions:** CAMERA
