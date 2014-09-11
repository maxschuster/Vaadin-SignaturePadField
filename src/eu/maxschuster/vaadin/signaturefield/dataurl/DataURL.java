/*
 * Copyright 2014 Max Schuster
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.maxschuster.vaadin.signaturefield.dataurl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import eu.maxschuster.vaadin.signaturefield.shared.MimeType;

/**
 * !! Experimental !!
 * 
 * Parses simple DataURLs of canvas elements
 * 
 * TODO Write real implementation of RFC 2397 (http://shadow2531.com/opera/testcases/datauri/data_uri_rules.html)
 * 
 * @author Max
 * 
 */
public class DataURL {
	
	private final MimeType mimeType;
	
	private final byte[] data;
	
	public DataURL(String dataURL) throws MalformedURLException {
		super();
		mimeType = findMimeType(dataURL);
		data = findData(dataURL);
	}
	
	public DataURL(MimeType mimeType, byte[] data) {
		super();
		this.mimeType = mimeType;
		this.data = data;
	}

	protected byte[] findData(String dataURL) throws MalformedURLException {
		int commaIndex = dataURL.indexOf(',');
		if (commaIndex == -1) {
			throw new MalformedURLException();
		}
		try {
		String base64String = dataURL.substring(commaIndex);
		return decodeBase64(base64String);
		} catch (IllegalArgumentException e) {
			throw new MalformedURLException();
		}
	}
	
	protected MimeType findMimeType(String dataURL) {
		int colonIndex = dataURL.indexOf(':');
		int semicolonIndex = dataURL.indexOf(';');
		String mimeTypeString = null;
		if (colonIndex != 4 || semicolonIndex < 6) {
			mimeTypeString = null;
		} else {
			mimeTypeString = dataURL.substring(colonIndex + 1, semicolonIndex);
		}
		return MimeType.valueOfMimeType(mimeTypeString);
	}
	
	protected byte[] decodeBase64(String string) throws IllegalArgumentException {
		return DatatypeConverter.parseBase64Binary(string);
	}
	
	protected String encodeBase64(byte[] data) throws IllegalArgumentException {
		return DatatypeConverter.printBase64Binary(data);
	}

	public MimeType getMimeType() {
		return mimeType;
	}

	public byte[] getData() {
		return data;
	}

	public String toDataURLString() {
		return "data:" + mimeType.getMimeType() + ";base64," + encodeBase64(data);
	}
	
	public InputStream toInputStream() {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(getData());
		return inputStream;
	}

	@Override
	public String toString() {
		return toDataURLString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result
				+ ((mimeType == null) ? 0 : mimeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataURL other = (DataURL) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		if (mimeType != other.mimeType)
			return false;
		return true;
	}
	
	
	
}