package dev.idx0;

public interface IResolver {
    boolean canResolve(DNSMessage msg);
    DNSMessage resolve(DNSMessage msg);

}