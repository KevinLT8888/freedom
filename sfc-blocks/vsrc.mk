sfc_blocks_dir := $(patsubst %/,%,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
SFC_TIMER_VSRCS := \
	$(sfc_blocks_dir)/vsrc/timer/Timers.v \
	$(sfc_blocks_dir)/vsrc/timer/TimersFrc.v \
	$(sfc_blocks_dir)/vsrc/timer/TimersPackage.v \
	$(sfc_blocks_dir)/vsrc/timer/TimersRevAnd.v \
	$(sfc_blocks_dir)/vsrc/timer/Timers_sv.sv
