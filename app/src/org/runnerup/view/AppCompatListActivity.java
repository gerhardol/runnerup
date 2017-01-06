/*
 * Copyright (C) 2017 gerhard.nospam@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.runnerup.view;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public abstract class AppCompatListActivity extends AppCompatActivity {

        private ListView mListView;

        protected ListView getListView() {
            if (mListView == null) {
                mListView = (ListView) findViewById(android.R.id.list);
            }
            return mListView;
        }

        protected void setListAdapter(ListAdapter adapter) {
            getListView().setAdapter(adapter);
        }

        protected ListAdapter getListAdapter() {
            ListAdapter adapter = getListView().getAdapter();
            if (adapter instanceof HeaderViewListAdapter) {
                return ((HeaderViewListAdapter)adapter).getWrappedAdapter();
            } else {
                return adapter;
            }
        }
        protected void onListItemClick(ListView lv, View v, int position, long id) {
            getListView().getOnItemClickListener().onItemClick(lv, v, position, id);
        }
    }

