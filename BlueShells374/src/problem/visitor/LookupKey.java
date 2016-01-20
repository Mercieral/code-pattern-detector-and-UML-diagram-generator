package problem.visitor;

class LookupKey {
	private VisitType visitType;
	private Class<?> targetType;
	
	public LookupKey(VisitType visitType, Class<?> targetType) {
		this.visitType = visitType;
		this.targetType = targetType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((visitType == null) ? 0 : visitType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LookupKey other = (LookupKey) obj;
		if (visitType != other.visitType)
			return false;
		
		if(!other.targetType.isAssignableFrom(this.targetType))
			return false;
		
		return true;
	}
}