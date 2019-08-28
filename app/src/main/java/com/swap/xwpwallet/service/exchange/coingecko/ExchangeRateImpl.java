/*
 * Copyright (c) 2017-2018 m2049r et al.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swap.xwpwallet.service.exchange.coingecko;

import android.support.annotation.NonNull;

import com.swap.xwpwallet.service.exchange.api.ExchangeException;
import com.swap.xwpwallet.service.exchange.api.ExchangeRate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ExchangeRateImpl implements ExchangeRate {

    private final String baseCurrency;
    private final String quoteCurrency;
    private final double rate;

    @Override
    public String getServiceName() {
        return "coingecko.com";
    }

    @Override
    public String getBaseCurrency() {
        return baseCurrency;
    }

    @Override
    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    @Override
    public double getRate() {
        return rate;
    }

    ExchangeRateImpl(@NonNull final String baseCurrency, @NonNull final String quoteCurrency, double rate) {
        super();
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.rate = rate;
    }

    ExchangeRateImpl(final JSONObject jsonObject, final boolean swapAssets) throws JSONException, ExchangeException {
        try {
			final Iterator<String> keys = jsonObject.keys();
            String key = keys.next();
			
            baseCurrency = "XWP";
            quoteCurrency = key.toUpperCase();

            double price = jsonObject.getDouble(key);
            this.rate = swapAssets ? (1d / price) : price;
        } catch (NoSuchElementException ex) {
            throw new ExchangeException(ex.getLocalizedMessage());
        }
    }
}
