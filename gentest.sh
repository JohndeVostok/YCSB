./bin/ycsb load rdma -P workloads/test -p type="load" -p path="testload.txt"
./bin/ycsb run rdma -P workloads/test -p type="run" -p path="testrun.txt"
