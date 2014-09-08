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

package eu.maxschuster.vaadin.signaturepadfield.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Console extends JavaScriptObject {
	
	protected Console() {}
	
	public static final native boolean isSupported() /*-{
		return typeof console === "object";
	}-*/;
	
	public static final native void log(Object...objects) /*-{
		if (typeof console === "object") {
			console.log.apply(console, objects);
		}
	}-*/;
	
}