package com.digitalmidges.jettest.ui.activities

import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digitalmidges.jettest.BaseApplication
import com.digitalmidges.jettest.R
import com.digitalmidges.jettest.databinding.ActivityMainBinding
import com.digitalmidges.jettest.ui.adapters.MenuAdapter
import com.digitalmidges.jettest.utils.*
import com.digitalmidges.jettest.viewModels.MainViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), HasAndroidInjector {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    private val mainViewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: ActivityMainBinding

    private lateinit var menuAdapter: MenuAdapter

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun setRootView(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun beforeInit() {
    }

    override fun initViews() {
    }

    override fun setDefaultViewsBehaviour() {
    }

    override fun afterInit() {
        initToolbar()
        initRecyclerViewAndSideMenu()
        initSideMenu()
    }


    override fun setViewsClickListener() {

        binding.fab.setOnClickListener {

            toast("TODO!!!!")

        }

    }

    override fun subscribeToViewModel() {
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbarInclude.toolbar)
        binding.toolbarInclude.imgHamburgerMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun initRecyclerViewAndSideMenu() {
        val menuList: ArrayList<MenuObject> = createMainMenuList()
        val linearLayoutManager = LinearLayoutManager(this)
        binding.apply {
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.setHasFixedSize(true)
            menuAdapter = MenuAdapter(this@MainActivity, menuList, object : MenuAdapter.IMenuAdapterCallback {

                override fun onItemClick(menuItem: MenuObject) {
                    toast("Item selected: " + menuItem.title)
                    closeDrawer()

                }

            })

            recyclerView.adapter = menuAdapter
        }

    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }


    private fun initSideMenu() {
        binding.toolbarInclude.toolbar.setNavigationOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }


    private fun createMainMenuList(): ArrayList<MenuObject> {

        // create dummy menu items for the drawerLayout

        val menuList: ArrayList<MenuObject> = ArrayList()

        // -----------------------//
        //PROFILE
        var menuItem = MenuObject(
            NAV_ITEM_PROFILE, getString(
                R.string.nav_item_profile
            )
        )
        menuList.add(menuItem)
        // -----------------------//
        //INBOX
        menuItem = MenuObject(
            NAV_ITEM_INBOX, getString(
                R.string.nav_item_inbox
            )
        )
        menuList.add(menuItem)
        // -----------------------//
        //MY ACCOUNT
        menuItem = MenuObject(
            NAV_ITEM_MY_ACCOUNT, getString(
                R.string.nav_item_my_account
            )
        )
        menuList.add(menuItem)
        // -----------------------//
        //FAVORITES
        menuItem = MenuObject(
            NAV_ITEM_FAVORITES, getString(
                R.string.nav_item_favorites
            )
        )
        menuList.add(menuItem)
        // -----------------------//
        //HELP/ABOUT
        menuItem = MenuObject(
            NAV_ITEM_HELP_ABOUT, getString(
                R.string.nav_item_help_about
            )
        )
        menuList.add(menuItem)
        // -----------------------//
        return menuList
    }


    data class MenuObject(val type: Int, val title: String)

}