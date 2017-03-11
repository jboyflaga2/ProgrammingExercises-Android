


## Switch between activities (Oct 28, 2016)

from http://www.androidhive.info/2011/08/how-to-switch-between-activities-in-android/

I learned about what context to pass when creating an Intent to show another activity: we should pass the activity context.

use `this` or `v.getContext()`

``` Java
        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //NOTE: look at your notes on "Application context vs. Activity context" so you will
                // understand why you passed v.getContext() where creating your intent that will show
                // the next screen.

                Intent openSecondScreenIntent = new Intent(v.getContext(), SecondScreenActivity.class);
                openSecondScreenIntent.putExtra("name", inputName.getText().toString());
                startActivity(openSecondScreenIntent);
            }
        });
```

## Material Design (Oct 28, 2016)

from http://www.androidhive.info/2015/04/android-getting-started-with-material-design/


