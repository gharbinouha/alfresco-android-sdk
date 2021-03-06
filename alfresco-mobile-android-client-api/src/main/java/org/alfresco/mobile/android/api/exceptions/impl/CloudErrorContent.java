/*******************************************************************************
 * Copyright (C) 2005-2012 Alfresco Software Limited.
 * 
 * This file is part of the Alfresco Mobile SDK.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 ******************************************************************************/
package org.alfresco.mobile.android.api.exceptions.impl;

import java.math.BigInteger;
import java.util.Map;

import org.alfresco.mobile.android.api.constants.CloudConstant;
import org.alfresco.mobile.android.api.exceptions.AlfrescoErrorContent;
import org.alfresco.mobile.android.api.utils.JsonUtils;
import org.apache.chemistry.opencmis.commons.impl.JSONConverter;

/**
 * Cloud Implementation of {@link AlfrescoErrorContent}.
 * 
 * @author Jean Marie Pascal
 */
public class CloudErrorContent implements AlfrescoErrorContent
{

    /** The HTTP error code. */
    private int code;

    /** The message associated to the exception. */
    private String message;

    /** The callstack. */
    private String callstack;

    /** The description url. */
    private String descriptionURL;

    /**
     * Parses json exception response and try to retrieve essential values.
     * 
     * @param errorContentValue the raw data from the HTTP request.
     * @return the cloud error content object.
     */
    @SuppressWarnings("unchecked")
    public static CloudErrorContent parseJson(String errorContentValue)
    {
        Map<String, Object> json = null;
        try
        {
            json = JsonUtils.parseObject(errorContentValue);
            json = (Map<String, Object>) json.get(CloudConstant.ERROR_VALUE);
        }
        catch (Exception e)
        {
            return null;
        }

        CloudErrorContent errorContent = new CloudErrorContent();
        BigInteger code = JSONConverter.getInteger(json, CloudConstant.STATUSCODE_VALUE);
        if (code == null) { return null; }
        errorContent.code = code.intValue();
        errorContent.message = JSONConverter.getString(json, CloudConstant.BRIEFSUMMARY_VALUE);
        errorContent.callstack = JSONConverter.getString(json, CloudConstant.STACKTRACE_VALUE);
        errorContent.descriptionURL = JSONConverter.getString(json, CloudConstant.DESCRIPTIONURL_VALUE);
        return errorContent;
    }

    /** {@inheritDoc} */
    public int getCode()
    {
        return code;
    }

    /** {@inheritDoc} */
    public String getMessage()
    {
        return message;
    }

    /** {@inheritDoc} */
    public String getStackTrace()
    {
        return callstack;
    }

    /**
     * Gets the description url associated to this error message.
     * 
     * @return the description url
     */
    public String getDescriptionURL()
    {
        return descriptionURL;
    }
}
