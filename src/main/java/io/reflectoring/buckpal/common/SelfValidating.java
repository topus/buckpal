package io.reflectoring.buckpal.common;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;

public abstract class SelfValidating<T> {

  private Validator validator;
  private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

  public SelfValidating() {
    validator = factory.getValidator();
  }

  /**
   * Evaluates all Bean Validations on the attributes of this
   * instance.
   */
  @SuppressWarnings("unchecked")
  protected void validateSelf() {
    Set<ConstraintViolation<T>> violations = validator.validate((T) this);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
