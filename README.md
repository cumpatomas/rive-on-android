# Rive On Android
Implementation of the amazing Rive software on Android Projects

## Responsive Animation Login Screen:

https://github.com/cumpatomas/rive-on-android/assets/102058754/508dff45-aa5e-46c5-a95e-6eb410fdc369


https://github.com/cumpatomas/rive-on-android/assets/102058754/87a5507e-1a88-4f41-9d0f-b017a5f22435

### Dependencies:

    implementation 'app.rive:rive-android:5.0.0'
    // During initialization, you may need to add a dependency
    // for Jetpack Startup
    implementation "androidx.startup:startup-runtime:1.1.1"


I also use view binding for the project, remember to add this on your gradle file: 

    buildFeatures{
        viewBinding true
    }

In the activity_main layout XML file you'll see that I implement a Constraint Layout
to act as a trigger of the rive view. This is because I don't know the way to make
the rive view clickable and set the click listener on it directly. lease let me know if
you find the correct way to do it :)

That's why I created a Constraint Layout (it will also work with an Image view also) constraint to 
the rive view to "follow" it.
```xml
 <androidx.constraintlayout.widget.ConstraintLayout
        android:id="@+id/loginButtonTrigger"
        android:layout_width="170dp"
        android:layout_height="80dp"
        android:layout_marginTop="24dp"
        app:layout_constraintEnd_toEndOf="@+id/loginButton"
        app:layout_constraintStart_toStartOf="@+id/loginButton"
        app:layout_constraintTop_toTopOf="@+id/loginButton">

    </androidx.constraintlayout.widget.ConstraintLayout>

    <app.rive.runtime.kotlin.RiveAnimationView
        android:id="@+id/loginButton"
        android:layout_width="170dp"
        android:layout_height="100dp"
        android:layout_marginTop="24dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@id/tiPassword"
        app:riveLoop="LOOP"
        app:riveResource="@raw/login_rive_button"
        app:riveStateMachine="State Machine 1"
        app:riveTraceAnimations="false" />
```

As the trigger view in this case the constraint Layout will stay in the back is necessary to
bring it to the front with this line in the MainActivity.kt:
```kotlin
binding.loginButtonTrigger.bringToFront()
```

## The Listeners:

```kotlin
/** we set the controllers when the text field "username" is focused or not
 * 
 */
 binding.username.setOnFocusChangeListener { view, boolean ->
            if (boolean) { // if true is focused
                binding.robot.setBooleanState("LoginRobot", "error", false) // this booleans are conditions  created on the Rive app and they are inside the rive files
                binding.robot.controller.setBooleanState("LoginRobot", "username", true) // changing the conditions will trigger the state machine paths created on the rive app
            } else {
                binding.robot.controller.setBooleanState("LoginRobot", "username", false)
            }
        }
```


```kotlin
/** here we check the input in the fields in this case only to check if it's empty to 
 trigger another animation state but could be to receive the amount of characters 
 to control the movement of the animation, etc..
 */
        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                username = p0.toString()
            }
        })
```
Then we control the login button click listener:

Inside the loginButton onclickListener you will find this line:
```kotlin
binding.loginButton.fireState("State Machine 1", "clicked")
// this fireState function starts the trigger created in Rive (instead of a boolean) 
//be careful with the names of the states machines and the conditionals must be the same including whitespaces
```
All this is very new to me and Rive is growing fast and is improving rapidly. So  I'm 
happy to receive any comment to improve the methods.
I experienced some refreshing issues in the animation, will try to smooth them as I get
more confidence and experience on Rive. 

Thanks!
