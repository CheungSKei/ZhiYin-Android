/**
 * Copyright 2014-present ZhiYin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zhiyin.android.im.exception;

/**
 * Represents an error condition specific for Android.
 */
public class ZYException extends RuntimeException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a new ZYException.
     */
    public ZYException() {
        super();
    }

    /**
     * Constructs a new ZYException.
     * 
     * @param message
     *            the detail message of this exception
     */
    public ZYException(String message) {
        super(message);
    }

    /**
     * Constructs a new ZYException.
     * 
     * @param message
     *            the detail message of this exception
     * @param throwable
     *            the cause of this exception
     */
    public ZYException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ZYException.
     * 
     * @param throwable
     *            the cause of this exception
     */
    public ZYException(Throwable throwable) {
        super(throwable);
    }
}
