package org.exigencecorp.bindgen.example.fixRawTypes;

import java.util.Enumeration;

import org.exigencecorp.bindgen.Bindable;

@Bindable
public class RawTypesExample {

    // TODO: Test field too
    // public Enumeration bare;
    public Enumeration<String> good;

    @SuppressWarnings("unchecked")
    public Enumeration getAttributeNames() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public void setAttributeNames(Enumeration e) {
    }

}
