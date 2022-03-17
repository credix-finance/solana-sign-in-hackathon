import React from "react";
import { Input as AntdInput, Form } from "antd";
import { InputProps as AntInputProps } from "antd/lib/input";
import { FormItemProps } from "antd/lib/form";

interface InputProps {
	/**
	 * Label text
	 */
	label: FormItemProps["label"];
	/**
	 * Name text
	 */
	name?: FormItemProps["name"];
	/**
	 * Optional description to provide more information about the input
	 */
	description?: string;
	/**
	 * Specifis whether the input has validation feedback
	 */
	hasFeedback?: FormItemProps["hasFeedback"];
	/**
	 * The prompt message. If not provided, the prompt message will be generated by the validation rule.
	 */
	help?: FormItemProps["help"];
	/**
	 * The result of the validation rules
	 */
	validateStatus?: "error" | "";
	/**
	 * Controls wheter the input can be interacted with or not.
	 * This differs from setting `disabled` to `true` in that by
	 * setting this option to true will remove any styling from the
	 * input that makes it look like an input.
	 */
	isDisplay?: boolean;
	placeholder?: AntInputProps["placeholder"];
	onClick?: AntInputProps["onClick"];
	type?: AntInputProps["type"];
	addonBefore?: AntInputProps["addonBefore"];
	suffix?: AntInputProps["suffix"];
	className?: AntInputProps["className"];
	children?: AntInputProps["children"];
	value?: AntInputProps["value"];
	disabled?: boolean;
	defaultValue?: AntInputProps["defaultValue"];
}

export const Input = ({
	label,
	name,
	description,
	children,
	value,
	hasFeedback,
	validateStatus,
	help,
	className = "",
	isDisplay = false,
	disabled = false,
	...props
}: InputProps) => {
	if (isDisplay) {
		return (
			<Form.Item label={label} className="font-bold text-base">
				<div className="font-medium text-base pt-2 pb-4">{value}</div>
			</Form.Item>
		);
	}

	return (
		<Form.Item
			label={label}
			help={help}
			hasFeedback={hasFeedback}
			validateStatus={validateStatus}
			className={`
				font-bold text-base mb-7
				${disabled && "text-neutral-60/40"}
				${hasFeedback && validateStatus === "error" && "border-error"}
			`}
		>
			{description && <div className="font-normal text-sm mt-0 mb-[10px]">{description}</div>}
			<Form.Item name={name} className="mb-0">
				<AntdInput
					disabled={disabled}
					value={value}
					size="large"
					className={`
						pl-4 pr-[25px] py-3 font-medium text-base bg-credix-primary placeholder-neutral-100/70 border-[0.5px] rounded-[1px]
						focus:shadow-none
						disabled:border-neutral-60/40
						${
							hasFeedback && validateStatus == "error"
								? "border-error focus:ring-error focus:border-error"
								: "border-neutral-60 focus:ring-neutral-100 focus:border-neutral-100"
						}
						${className}
					`}
					{...props}
				>
					{children}
				</AntdInput>
			</Form.Item>
		</Form.Item>
	);
};
