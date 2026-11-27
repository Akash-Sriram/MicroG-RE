# MicroG-RE Rules & Build Protocol

## 1. Cloud-First CI/CD Build Enforcement
> [!IMPORTANT]
> **NEVER build MicroG-RE locally via Gradle.**
> Do NOT run `./gradlew assemble...` or `./gradlew build` on the host machine. Local builds consume excessive memory and CPU resources. All compilation must be delegated to GitHub Actions.

### Build & Artifact Workflow:
1. **Commit Changes**: Commit all code changes to branch `feat/phenotype`.
2. **Push to Remote**: Push commits to the user fork remote:
   ```bash
   git push fork feat/phenotype
   ```
3. **Trigger / Monitor Workflow**:
   The workflow `.github/workflows/test-build.yml` triggers automatically on push to `feat/phenotype`. You can also trigger or inspect it with:
   ```bash
   gh run list --workflow=test-build.yml --repo Akash-Sriram/MicroG-RE -L 3
   gh run watch <run_id> --repo Akash-Sriram/MicroG-RE
   ```
4. **Download Artifact**:
   Once the workflow run succeeds, download the resulting debug APK:
   ```bash
   gh run download <run_id> --name microg-default-debug-apk --repo Akash-Sriram/MicroG-RE --dir /home/akashsriram/Projects/Morphe/outputs/microg
   ```
5. **Deploy to Device**:
   Install non-intrusively to the test device:
   ```bash
   adb -s 192.168.1.11:5555 install -r /home/akashsriram/Projects/Morphe/outputs/microg/*.apk
   ```

---

## 2. Testing App Protocol (Google Photos)
- **Always Use De-Vanced**: Build test APKs using De-Vanced (`/home/akashsriram/Projects/Morphe/sources/RookieEnough/De-Vanced`).
- **Single Patch Only**: Apply **ONLY** the `GmsCore support` patch alone (`-e "GmsCore support"`).
- **No Client Hacks**: Absolutely NO fake download state hooks, NO hardcoded flag overrides, and NO seeder scripts in Google Photos.
- **MicroG-RE Driven**: All features (Phenotype flag serving, MDD file group registration, fonts, authentication) must function organically through MicroG-RE's native services and ContentProviders.

---

## 3. Device Interaction Rules
- **Target Device**: POCO F1 (`192.168.1.11:5555`). Never connect to or touch Galaxy S24 or other devices unless explicitly directed.
- **NO Screenshots**: NEVER execute `screencap`, screen captures, or screen recordings on any device.
- **NO Touch or Input Events**: NEVER send touch inputs, tap events, swipe gestures, key events, or text inputs (`adb shell input tap`, `adb shell input swipe`, `adb shell input keyevent`, `adb shell input text`, etc.).
- **Non-Intrusive Verification**: Verify purely through `dumpsys`, `pm`, `logcat`, SQLite queries, or ask the user to interact.
