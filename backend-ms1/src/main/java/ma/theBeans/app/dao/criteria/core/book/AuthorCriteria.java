package ma.theBeans.app.dao.criteria.core.book;



import ma.theBeans.app.thePackage.criteria.BaseCriteria;

public class AuthorCriteria extends  BaseCriteria  {

    private String code;
    private String codeLike;
    private String label;
    private String labelLike;
    private String fullName;
    private String fullNameLike;



    public String getCode(){
        return this.code;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCodeLike(){
        return this.codeLike;
    }
    public void setCodeLike(String codeLike){
        this.codeLike = codeLike;
    }

    public String getLabel(){
        return this.label;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabelLike(){
        return this.labelLike;
    }
    public void setLabelLike(String labelLike){
        this.labelLike = labelLike;
    }

    public String getFullName(){
        return this.fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public String getFullNameLike(){
        return this.fullNameLike;
    }
    public void setFullNameLike(String fullNameLike){
        this.fullNameLike = fullNameLike;
    }


}
