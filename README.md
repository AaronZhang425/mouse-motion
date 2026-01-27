This program will only run in a linux environment

This program reads raw mouse data and converts it to meters. To get mouse data, reads files within the /dev/input/eventX file associated with a mouse where X represents any number.

This program needs to part of the input user group to access the system files it needs to access.

To run this program, first run build.sh to compile and then run run.sh to run it.

If measurements are off, try adjusting the DPI constant in main or try placing the mosue on a different surface.