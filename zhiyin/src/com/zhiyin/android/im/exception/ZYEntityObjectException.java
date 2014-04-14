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
 * Represents an error specific to the {@link com.zhiyin.im.model.EntityObject EntityObject} class.
 */
public class ZYEntityObjectException extends ZYException {
    static final long serialVersionUID = 1;

    /**
     * Constructs a new ZYEntityObjectException.
     */
    public ZYEntityObjectException() {
        super();
    }

    /**
     * Constructs a new ZYEntityObjectException.
     * 
     * @param message
     *            the detail message of this exception
     */
    public ZYEntityObjectException(String message) {
        super(message);
    }

    /**
     * Constructs a new ZYEntityObjectException.
     * 
     * @param message
     *            the detail message of this exception
     * @param throwable
     *            the cause of this exception
     */
    public ZYEntityObjectException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * Constructs a new ZYEntityObjectException.
     * 
     * @param throwable
     *            the cause of this exception
     */
    public ZYEntityObjectException(Throwable throwable) {
        super(throwable);
    }
}
