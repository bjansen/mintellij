package com.github.bjansen.mintellij.psi;

import com.intellij.psi.stubs.IStubElementType;

@SuppressWarnings({"rawtypes", "unused"})
public interface MintStubs {

    IStubElementType MODULE = MintModuleStubElementType.INSTANCE;
    IStubElementType COMPONENT = MintComponentStubElementType.INSTANCE;
    IStubElementType RECORD = MintRecordStubElementType.INSTANCE;
    IStubElementType STORE = MintStoreStubElementType.INSTANCE;
    IStubElementType ENUM = MintEnumStubElementType.INSTANCE;
}
