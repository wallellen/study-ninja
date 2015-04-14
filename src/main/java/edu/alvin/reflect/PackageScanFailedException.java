package edu.alvin.reflect;

public class PackageScanFailedException extends RuntimeException {
    public PackageScanFailedException(String s, Exception e) {
        super(s, e);
    }
}
