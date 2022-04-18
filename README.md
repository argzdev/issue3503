# issue 3503
### Prerequisite
- Add `google-services.json`
- Enable RTDB
### Summary
- Adding `addChildEventListener` on a `DatabaseReference` increases the memory usage by ~30 to ~40 mb. Changing the values in `limitToLast()` or excluding it will result to the same memory overhead being applied (approx. ~30 - ~40 mb) e.g. `database.limitToLast(1).addChildEventListener(childEventListener)`
### Steps to reproduce
1. After adding `google-services.json`, open app, click the `Add Data` button, and wait until all 4000 workouts have been added
2. Close and reopen the app for a fresh memory usage.
4. Open Android Profiler, take note of the current memory usage, and then press `Add Listener` button.
6. Check the difference of the current memory usage and after pressing the button. (roughly ~30 mb overhead difference)
7. Try changing the value in `database.limitToLast(1).addChildEventListener(childEventListener)` or use either with or without `limitToLast(1)`, the overhead difference is still approximately the same.
