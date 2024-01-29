<template>
	<component
		:is="tag"
		class="flex justify-center items-center gap-2 rounded-[14px] py-[13px] px-6 relative"
		:class="[buttonClass, wrapperClass]"
		:disabled="isDisabled"
		:href="tag === 'a' ? href : undefined"
		:target="tag === 'a' ? target : undefined"
		:value="tag === 'input' ? value : undefined"
		:type="tag === 'button' ? buttonType : ''"
		@click="onClick"
	>
		<div v-if="!isLoading" class="flex gap-2 items-center">
			<slot name="start"></slot>
			<Icon v-if="iconLeft" :name="iconLeft" :class="iconClass" />
			<span v-if="!isLoading" class="cursor-pointer text-primary-contrast" :class="isBold ? 'font-bold' : ''">{{ text }}</span>
			<slot></slot>
			<Icon v-if="iconRight" :name="iconRight" :class="iconClass" />
			<slot name="end"></slot>
		</div>
	</component>
</template>

<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
	props: {
		text: {
			type: String,
			default: '',
		},
		type: {
			type: String,
			default: 'primary',
		},
		wrapperClass: {
			type: String,
			default: '',
		},
		size: {
			type: String,
			default: 'medium',
		},
		isLoading: {
			type: Boolean,
			default: false,
		},
		isBold: {
			type: Boolean,
			default: false,
		},
		isRounded: {
			type: Boolean,
			default: false,
		},
		isOutlined: {
			type: Boolean,
			default: false,
		},
		isFocused: {
			type: Boolean,
			default: false,
		},
		isInverted: {
			type: Boolean,
			default: false,
		},
		isHovered: {
			type: Boolean,
			default: false,
		},
		isActive: {
			type: Boolean,
			default: false,
		},
		isSelected: {
			type: Boolean,
			default: false,
		},
		isDisabled: {
			type: Boolean,
			default: false,
		},
		isExpanded: {
			type: Boolean,
			default: false,
		},
		iconLeft: {
			type: String,
			default: '',
		},
		iconRight: {
			type: String,
			default: '',
		},
		nativeType: {
			type: String,
			default: 'button',
		},
		tag: {
			type: String,
			default: 'button',
		},
		href: {
			type: String,
			default: '',
		},
		target: {
			type: String,
			default: '',
		},
		value: {
			type: String,
			default: '',
		},
		buttonType: {
			type: String,
			default: 'button',
		},
	},
	emits: ['onClick'],
	setup(props, ctx) {
		const buttonClass = computed(() => {
			return [
				'button',
				{
					'border border-transparent': !props.isOutlined,
					[`bg-${props.type}-default text-${props.type}-contrast`]: !props.isOutlined,
					[`hover:bg-${props.type}-shade`]: !props.isHovered && !props.isOutlined && !props.isDisabled,
					[`hover:bg-${props.type}-default hover:text-${props.type}-contrast`]: !props.isHovered && props.isOutlined && !props.isDisabled,
					'text-sm py-[6px]': props.size === 'small',
					'text-base': props.size === 'medium',
					'text-lg': props.size === 'large',
					'rounded-full': props.isRounded,
					[`border border-${props.type}-default text-${props.type}-default`]: props.isOutlined,
					focus: props.isFocused,
					invert: props.isInverted,
					active: props.isActive,
					selected: props.isSelected,
					'w-full': props.isExpanded,
					'opacity-50 cursor-not-allowed': props.isDisabled,
				},
			];
		});

		const iconClass = computed(() => {
			return [
				{
					'h-5 w-4': props.size === 'small',
					'h-6 w-5': props.size === 'medium',
					'h-10 w-9': props.size === 'large',
				},
			];
		});

		const onClick = () => {
			if (!props.isDisabled && !props.isLoading) {
				ctx.emit('onClick');
			}
		};

		return {
			buttonClass,
			iconClass,
			onClick,
		};
	},
});
</script>

<style></style>
