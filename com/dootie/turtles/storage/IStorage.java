package com.dootie.turtles.storage;


public interface IStorage {
    public void read() throws StorageException;

    public void write() throws StorageException;
}

