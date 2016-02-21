#!/bin/bash

sudo tc qdisc del dev lo root
sudo tc qdisc add dev lo root netem loss 50%
sudo tc qdisc change dev lo root netem delay 100ms 20ms distribution normal
