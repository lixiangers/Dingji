package com.lixiangers.dingji.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lixiangers.dingji.R;
import com.lixiangers.dingji.util.AddressData;
import com.lixiangers.dingji.widget.AbstractWheelTextAdapter;
import com.lixiangers.dingji.widget.ArrayWheelAdapter;
import com.lixiangers.dingji.widget.OnWheelChangedListener;
import com.lixiangers.dingji.widget.WheelView;

import java.util.List;

import static com.lixiangers.dingji.util.StringUtil.isBlank;
import static java.util.Arrays.asList;

public class LocationPopupWindow extends LinearLayout {
    private WheelView countyView;
    private WheelView provinceView;
    private WheelView cityView;

    private String provinceString;
    private String cityString;
    private String countyString;

    private Context context;

    private onAreaChangeListener onAreaChangeListener = new onAreaChangeListener() {
        @Override
        public void areaChange(String provinceString, String cityString, String countyString) {

        }
    };

    public LocationPopupWindow(Context context) {
        super(context);
        this.context = context;
        initUi();

    }

    public LocationPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initUi();

    }

    public LocationPopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initUi();
    }

    public void setLocation(String provinceString, String cityString, String countyString) {
        this.provinceString = provinceString;
        this.cityString = cityString;
        this.countyString = countyString;
        int i = asList(AddressData.PROVINCES).indexOf(provinceString);
        provinceView.setCurrentItem(i);
    }

    public void setDefaultAddress() {
        setLocation(AddressData.PROVINCES[0], AddressData.CITIES[0][0], AddressData.COUNTIES[0][0][0]);
    }

    private void initUi() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cities_layout, this);

        provinceView = (WheelView) findViewById(R.id.province);
        cityView = (WheelView) findViewById(R.id.city);
        countyView = (WheelView) findViewById(R.id.county);
        provinceView.setVisibleItems(5);
        countyView.setVisibleItems(5);
        cityView.setVisibleItems(5);

        final String cities[][] = AddressData.CITIES;
        final String counties[][][] = AddressData.COUNTIES;

        provinceView.setViewAdapter(new CountryAdapter(context));
        initListener(cities, counties);
    }

    public void setAreaChangeListener(onAreaChangeListener onAreaChangeListener) {
        this.onAreaChangeListener = onAreaChangeListener;
    }

    private void initListener(final String[][] cities, final String[][][] counties) {
        provinceView.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(cityView, cities, newValue);
            }
        });

        cityView.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCounty(countyView, counties, provinceView.getCurrentItem(), newValue);
            }
        });

        countyView.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateArea(provinceView, cityView, countyView);
            }
        });

        List<String> strings = asList(AddressData.PROVINCES);
        int index = isBlank(provinceString) ? 1 : strings.indexOf(provinceString);
        provinceView.setCurrentItem(index);
    }

    /**
     * Updates the cityView wheel
     */
    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter1 =
                new ArrayWheelAdapter<String>(context, cities[index]);
        adapter1.setTextSize(18);
        city.setViewAdapter(adapter1);
        int i = asList(cities[index]).indexOf(cityString);
        city.setCurrentItem(isBlank(cityString) ? cities[index].length / 2 : i);
    }

    /**
     * Updates the countyView wheel
     */
    private void updateCounty(WheelView countyView, String counties[][][], int index, int index2) {
        ArrayWheelAdapter<String> adapter2 =
                new ArrayWheelAdapter<String>(context, counties[index][index2]);
        adapter2.setTextSize(18);

        countyView.setViewAdapter(adapter2);
        int i = asList(counties[index][index2]).indexOf(countyString);
        int currentItem = isBlank(countyString) ? counties[index][index2].length / 2 : i;
        countyView.scroll(0, 1);
        countyView.setCurrentItem(currentItem);
    }

    private void updateArea(WheelView country, WheelView city, WheelView ccity) {
        clearAreaInfo();
        onAreaChangeListener.areaChange(AddressData.PROVINCES[country.getCurrentItem()],
                AddressData.CITIES[country.getCurrentItem()][city.getCurrentItem()],
                AddressData.COUNTIES[country.getCurrentItem()][city.getCurrentItem()][ccity.getCurrentItem()]);
    }

    private void clearAreaInfo() {
        provinceString = "";
        cityString = "";
        countyString = "";
    }

    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        private String countries[] = AddressData.PROVINCES;

        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.country_layout, NO_RESOURCE);

            setItemTextResource(R.id.country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return countries.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    public interface onAreaChangeListener {
        void areaChange(String provinceString, String cityString, String countyString);
    }
}
