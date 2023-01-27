package com.example.ambu.view;

import androidx.fragment.app.Fragment;


public interface Navigation {
    void navigateTo(Fragment fragment, boolean addToBackstack);
}