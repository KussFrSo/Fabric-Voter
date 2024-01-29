import type { Config } from 'tailwindcss';

const COLORS = ['primary', 'secondary', 'tertiary', 'success', 'warning', 'danger', 'medium', 'light', 'link', 'muted'];

export default <Partial<Config>>{
	safelist: [
		...COLORS.map((color) => {
			return [
				`bg-${color}-default`,
				`bg-${color}-tint`,
				`bg-${color}-shade`,
				`bg-${color}-contrast`,
				`text-${color}-default`,
				`text-${color}-shade`,
				`text-${color}-tint`,
				`text-${color}-contrast`,
				`border-${color}-default`,
				`border-${color}-tint`,
				`border-${color}-shade`,
				`border-${color}-contrast`,
				`hover:bg-${color}-default`,
				`hover:bg-${color}-tint`,
				`hover:bg-${color}-shade`,
				`hover:bg-${color}-contrast`,
				`hover:text-${color}-default`,
				`hover:text-${color}-shade`,
				`hover:text-${color}-tint`,
				`hover:text-${color}-contrast`,
			];
		}).flatMap((arr) => arr),
	],
	theme: {
		extend: {
			fontFamily: {
				lato: ['Inter', 'sans-serif'],
				comfortaa: ['Comfortaa', 'cursive'],
			},
			colors: {
				...COLORS.reduce((obj, item) => {
					return {
						...obj,
						[item]: {
							10: `var(--color-${item}-10)`,
							20: `var(--color-${item}-20)`,
							30: `var(--color-${item}-30)`,
							40: `var(--color-${item}-40)`,
							50: `var(--color-${item}-50)`,
							60: `var(--color-${item}-60)`,
							default: `var(--color-${item})`,
							tint: `var(--color-${item}-tint)`,
							shade: `var(--color-${item}-shade)`,
							contrast: `var(--color-${item}-contrast)`,
						},
					};
				}, {}),
			},
		},
	},
};

