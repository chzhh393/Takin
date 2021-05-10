/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.shulie.surge.data.runtime.disruptor.dsl;

import io.shulie.surge.data.runtime.disruptor.EventProcessor;
import io.shulie.surge.data.runtime.disruptor.EventHandler;
import io.shulie.surge.data.runtime.disruptor.Sequence;
import io.shulie.surge.data.runtime.disruptor.SequenceBarrier;

import java.util.concurrent.Executor;

/**
 * <p>Wrapper class to tie together a particular event processing stage</p>
 *
 * <p>Tracks the event processor instance, the event handler instance, and sequence barrier which the stage is attached to.</p>
 *
 * @param T the type of the configured {@link EventHandler}
 */
class EventProcessorInfo<T> implements ConsumerInfo
{
	private final EventProcessor eventprocessor;
	private final EventHandler<T> handler;
	private final SequenceBarrier barrier;
	private boolean endOfChain = true;

	EventProcessorInfo(final EventProcessor eventprocessor, final EventHandler<T> handler, final SequenceBarrier barrier)
	{
		this.eventprocessor = eventprocessor;
		this.handler = handler;
		this.barrier = barrier;
	}

	public EventProcessor getEventProcessor()
	{
		return eventprocessor;
	}

	@Override
	public Sequence[] getSequences()
	{
		return new Sequence[] { eventprocessor.getSequence() };
	}

	public EventHandler<T> getHandler()
	{
		return handler;
	}

	@Override
	public SequenceBarrier getBarrier()
	{
		return barrier;
	}

	@Override
	public boolean isEndOfChain()
	{
		return endOfChain;
	}

	@Override
	public void start(final Executor executor)
	{
		executor.execute(eventprocessor);
	}

	@Override
	public void halt()
	{
		eventprocessor.halt();
	}

	/**
	 *
	 */
	@Override
	public void markAsUsedInBarrier()
	{
		endOfChain = false;
	}

	@Override
	public boolean isRunning()
	{
		return eventprocessor.isRunning();
	}
}
